/*
 * Developed by Matthias Percelay. Created on 27/03/19 23:01.
 * Last modified 27/03/19 23:01
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

    private int currentDrawHeight = 0;

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();

        canvasPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> updateScrolling());
        canvasPane.hvalueProperty().addListener((observable, oldValue, newValue) -> updateScrolling());
        canvasPane.vvalueProperty().addListener((observable, oldValue, newValue) -> updateScrolling());

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

    void drawSegment(int drawHeight, int step, int xStart, int xEnd, int pixelSize) {
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
        int stepsToPaint = (int)Math.floor(canvas.getHeight() / pixelSize);
        stepsToPaint = Math.min(stepsToPaint, model.getSpaceTimeDiagram().getConfCount() - verticalOffset);
        int cellsToDraw = (int)Math.floor(canvas.getWidth() / pixelSize);
        cellsToDraw = Math.min(cellsToDraw, model.getSpaceTimeDiagram().getMaxConfSize());

        for (int i = verticalOffset; i < verticalOffset + stepsToPaint ; i++) {
            drawSegment(i * pixelSize, i, horizontalOffset, horizontalOffset + cellsToDraw, pixelSize);
        }
    }

    public void updateScrolling(){
        double hValue = canvasPane.getHvalue();
        double vValue = canvasPane.getVvalue();
        System.out.println(hValue + ", " + vValue);

        double portWidth = canvasPane.getViewportBounds().getWidth();
        canvas.setWidth(portWidth);
        double portHeight = canvasPane.getViewportBounds().getHeight();
        canvas.setHeight(portHeight);

        double canvasX = (canvasPane.getContent().getBoundsInParent().getWidth() - portWidth) * hValue;
        double canvasY = (canvasPane.getContent().getBoundsInParent().getHeight() - portHeight) * vValue;

        canvas.relocate(canvasX, canvasY);

        int horizontalOffset = (int)Math.floor(sizePane.getWidth() / pixelSize * hValue);
        int verticalOffset = (int)Math.floor(sizePane.getHeight() / pixelSize * vValue);

        if (model.hasRun()) {
            paintWholeCanvas(horizontalOffset, verticalOffset);
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
    }

    public void setCanvasWidth(double width) {
        canvas.setWidth(width);
    }

    public void setCanvasHeight(double height) {
        canvas.setHeight(height);
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

    public void drawAutomaton() {
        for (int i = 0; i < model.getMaxSteps(); i++) {
            drawLinePixelSize(i * pixelSize, model.getColors(i), pixelSize);
        }
    }
}
