/*
 * Developed by Matthias Percelay. Created on 27/03/19 23:01.
 * Last modified 27/03/19 23:01
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Controller for the canvas and its containing pane
 */
public class CanvasController {
    private Model model = null;

    @FXML
    ScrollPane canvasPane;

    @FXML
    Pane sizePane;

    @FXML
    Canvas canvas;

    GraphicsContext ctx = null;

    private int pixelSize = 4;

    private boolean drawDistMode = false;

    private int currentDrawHeight = 0;

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();

        canvasPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> updateScrolling());
        canvasPane.hvalueProperty().addListener((observable, oldValue, newValue) -> updateScrolling());
        canvasPane.vvalueProperty().addListener((observable, oldValue, newValue) -> updateScrolling());

        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int clickX = (int)Math.floor(mouseEvent.getX() / pixelSize);
                int clickY = (int)Math.floor(mouseEvent.getY() / pixelSize);
                System.out.println(clickX + ", " + clickY);
            }
        };

        sizePane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                int clickX = (int)Math.floor(mouseEvent.getX() / pixelSize);
                int clickY = (int)Math.floor(mouseEvent.getY() / pixelSize);
                model.getSpaceTimeDiagram().editStartingConfiguration(clickX, model.getCurrentEditingState());
                model.runAutomaton();
                updateScrolling();
            }
        });

        sizePane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("mouse pressed " + mouseEvent.getX() + " " + mouseEvent.getY());
                //mouseEvent.setDragDetect(true);
            }
        });

        sizePane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("mouse released " + mouseEvent.getX() + " " + mouseEvent.getY());
            }
        });

        sizePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("mouse dragged" + mouseEvent.getX() + " " + mouseEvent.getY());
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
        PixelWriter pw = ctx.getPixelWriter();

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

        PixelWriter pw = ctx.getPixelWriter();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < pixelSize; j++) {
                for (int k = 0; k < pixelSize; k++) {
                    pw.setColor(pixelSize * i + j, drawHeight + k, model.getStateColor(data[i]));
                }
            }
        }
    }

    void paintWholeCanvas(int horizontalOffset, int verticalOffset) {
        clearCanvas();
        int stepsToPaint = (int)Math.floor(canvas.getHeight() / pixelSize);
        stepsToPaint = Math.min(stepsToPaint, model.getSpaceTimeDiagram().getConfCount() - verticalOffset);
        int cellsToDraw = (int)Math.floor(canvas.getWidth() / pixelSize);
        cellsToDraw = Math.min(cellsToDraw, model.getSpaceTimeDiagram().getMaxConfSize());

        for (int i = 0; i < stepsToPaint; i++) {
            drawConfSegment(i * pixelSize, verticalOffset + i, horizontalOffset, horizontalOffset + cellsToDraw, pixelSize);
        }
    }

    void paintDistribution(int horizontalOffset) {
        clearCanvas();
        LocalRule[] rules = model.getArrayOfRules(horizontalOffset, horizontalOffset + (int)Math.ceil(canvas.getWidth() / pixelSize));
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

        PixelWriter pw = ctx.getPixelWriter();
        for (int i = 0; i < (pixelSize * (int)Math.ceil(canvas.getWidth() / pixelSize)); i++) {
            for (int j = 0; j < canvas.getHeight(); j++) {
                pw.setColor(i, j, colors[(int)Math.ceil(i/pixelSize)]);
            }
        }

    }

    public void updateScrolling(){
        double hValue = canvasPane.getHvalue();
        double vValue = canvasPane.getVvalue();

        Bounds bounds = canvasPane.getViewportBounds();
        double portWidth = bounds.getWidth();
        canvas.setWidth(portWidth);
        double portHeight = bounds.getHeight();
        canvas.setHeight(portHeight);

        double canvasX = (canvasPane.getContent().getBoundsInParent().getWidth() - portWidth) * hValue;

        double canvasY = (canvasPane.getContent().getBoundsInParent().getHeight() - portHeight) * vValue;

        canvas.relocate(canvasX, canvasY);
        canvasPane.setMaxWidth(portWidth);

        int horizontalOffset = (int)Math.floor((sizePane.getWidth() - canvasPane.getWidth()) / pixelSize * hValue);
        int verticalOffset = (int)Math.floor((sizePane.getHeight() - canvasPane.getHeight()) / pixelSize * vValue);

        if (drawDistMode) {
            paintDistribution(horizontalOffset);
        } else {
            if (model.hasRun()) {
                paintWholeCanvas(horizontalOffset, verticalOffset);
            }
        }
    }

    void setSizePaneDims(int width, int height, int pixelSize) {
        sizePane.setMinSize(width * pixelSize, height * pixelSize);
        sizePane.setMaxSize(width * pixelSize, height * pixelSize);
    }


    /**
     * clears the canvas
     */
    public void clearCanvas() {
        ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        currentDrawHeight = 0;
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

    public void switchDistMode() {
        drawDistMode = !drawDistMode;
    }
}
