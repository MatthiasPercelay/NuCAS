/*
 * Developed by Matthias Percelay. Created on 27/03/19 23:01.
 * Last modified 27/03/19 23:01
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Controller for the canvas and its containing pane
 */
public class CanvasController {

    @FXML
    public AnchorPane canvasRegion;
    @FXML
    public Canvas distCanvas;
    @FXML
    public Canvas confCanvas;
    @FXML
    public ScrollBar horizontalBar;
    @FXML
    public ScrollBar verticalBar;
    @FXML
    public VBox innerVBox;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public HBox outerHBox;

    private Model model = null;

    GraphicsContext distCTX = null;
    GraphicsContext confCTX = null;

    private int pixelSize = 4;

    private int currentDrawHeight = 0;

    private int pressedX = 0;
    private boolean rightClickPressed = false;

    public void initialize() {
        confCTX = confCanvas.getGraphicsContext2D();
        distCTX = distCanvas.getGraphicsContext2D();

        canvasRegion.heightProperty().addListener(((observable, oldValue, newValue) -> {
            confCanvas.setHeight(canvasRegion.getHeight() - distCanvas.getHeight() - horizontalBar.getHeight());
            updateScrolling();
        }));

        canvasRegion.widthProperty().addListener(((observable, oldValue, newValue) -> {
            distCanvas.setWidth(canvasRegion.getWidth() - verticalBar.getWidth());
            confCanvas.setWidth(canvasRegion.getWidth() - verticalBar.getWidth());
            updateScrolling();
        }));

        verticalBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateScrolling();
        });

        horizontalBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateScrolling();
        });

        confCanvas.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            if (scrollEvent.getDeltaY() < 0) {
                verticalBar.increment();
            } else {
                verticalBar.decrement();
            }
        });

        confCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                int clickX = (int)Math.floor(mouseEvent.getX() / pixelSize);
                int clickY = (int)Math.floor(mouseEvent.getY() / pixelSize);
                int offset = model.getSpaceTimeDiagram().getMaxDistOffset();
                model.getSpaceTimeDiagram().editStartingConfiguration((int)horizontalBar.getValue() + clickX - offset, model.getCurrentEditingState());
                updateScrolling();
            }
        });

        confCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                pressedX = (int)Math.floor(mouseEvent.getX() / pixelSize);
                rightClickPressed = true;
            }
        });

        confCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY && rightClickPressed) {
                int endX = (int) Math.floor(mouseEvent.getX() / pixelSize);
                int offset = model.getSpaceTimeDiagram().getMaxDistOffset();
                for (int i = pressedX; i <= endX; i++) {
                    model.getSpaceTimeDiagram().editStartingConfiguration((int)horizontalBar.getValue() + i - offset, model.getCurrentEditingState());
                }
                rightClickPressed = false;
            }
        });
    }

    /**
     * draws a line of pixelSize-sized squares at the given height based on the color data in content
     * @param height the height in real pixels at which to start drawing
     * @param content array of colors describing the "pixels" to draw
     * @param pixelSize size of the "pixels"
     */
    public void drawLinePixelSize(int height, Color[] content, int pixelSize) {
        PixelWriter pw = confCTX.getPixelWriter();

        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < pixelSize; j++) {
                for (int k = 0; k < pixelSize; k++) {
                    pw.setColor(pixelSize * i + j, height + k, content[i]);
                }
            }
        }
    }

    void drawConfSegment(int drawHeight, int step, int xStart, int xEnd, int pixelSize) {
        int[] data = model.getSpaceTimeDiagram().getSegment(xStart, xEnd, step);

        PixelWriter pw = confCTX.getPixelWriter();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < pixelSize; j++) {
                for (int k = 0; k < pixelSize; k++) {
                    pw.setColor(pixelSize * i + j, drawHeight + k, model.getStateColor(data[i]));
                }
            }
        }
    }

    void paintWholeCanvas(int horizontalOffset, int verticalOffset) {
        clearConfCanvas();
        int stepsToPaint = (int)Math.floor(confCanvas.getHeight() / pixelSize);
        stepsToPaint = Math.min(stepsToPaint, model.getSpaceTimeDiagram().getConfCount() - verticalOffset);
        int cellsToDraw = (int)Math.floor(confCanvas.getWidth() / pixelSize);
        cellsToDraw = Math.min(cellsToDraw, model.getSpaceTimeDiagram().getMaxConfSize());

        int distOffset = model.getSpaceTimeDiagram().getMaxDistOffset();

        for (int i = 0; i < stepsToPaint; i++) {
            drawConfSegment(i * pixelSize, verticalOffset + i, horizontalOffset - distOffset, horizontalOffset + cellsToDraw - distOffset, pixelSize);
        }
    }

    //TODO : work out a better way to handle distribution
    void paintDistribution(int horizontalOffset) {
        clearDistCanvas();
        int offset = horizontalOffset - model.getSpaceTimeDiagram().getMaxDistOffset();
        LocalRule[] rules = model.getArrayOfRules(offset, offset + (int)Math.ceil(distCanvas.getWidth() / pixelSize));
        double numRules = 0;
        HashMap<LocalRule, Double> diffRules = new HashMap<>();
        for (int i = 0; i < rules.length; i++) {
            if (!diffRules.containsKey(rules[i])) {
                diffRules.put(rules[i], numRules);
                numRules++;
            }
        }

        Color[] colors = new Color[rules.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = Color.WHITE.interpolate(Color.BLACK, diffRules.get(rules[i]) / numRules);
        }

        PixelWriter pw = distCTX.getPixelWriter();
        for (int i = 0; i < (pixelSize * (int)Math.ceil(distCanvas.getWidth() / pixelSize)); i++) {
            for (int j = 0; j < distCanvas.getHeight(); j++) {
                pw.setColor(i, j, colors[(int)Math.ceil(i/pixelSize)]);
            }
        }

    }

    public void updateScrolling(){
        double hValue = horizontalBar.getValue();
        double vValue = verticalBar.getValue();

        int horizontalOffset = (int)hValue;
        int verticalOffset = (int)vValue;

        if (model.hasRun()) {
            paintDistribution(horizontalOffset);
            paintWholeCanvas(horizontalOffset, verticalOffset);
        }
    }

    public void setupScrollbars(int confWidth, int maxSteps) {
        setScrollDimensions(confWidth, maxSteps);
        setScrollIncrements();
    }

    public void setScrollDimensions(int h, int v) {
        horizontalBar.setMin(0);
        horizontalBar.setMax(h);

        verticalBar.setMin(0);
        verticalBar.setMax(v);
    }

    public void setScrollIncrements() {
        horizontalBar.setUnitIncrement(4);
        verticalBar.setUnitIncrement(4);

        horizontalBar.setBlockIncrement(16);
        verticalBar.setBlockIncrement(16);
    }

    /**
     * clears the canvas
     */
    public void clearConfCanvas() {
        confCTX.clearRect(0, 0, confCanvas.getWidth(), confCanvas.getHeight());
        currentDrawHeight = 0;
    }

    public void clearDistCanvas() {
        distCTX.clearRect(0, 0, distCanvas.getWidth(), distCanvas.getHeight());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(int newSize) {
        pixelSize = newSize;
    }

}
