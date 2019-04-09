/*
 * Developed by Matthias Percelay. Created on 27/03/19 23:01.
 * Last modified 27/03/19 23:01
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ServiceConfigurationError;

/**
 * Controller for the canvas and its containing pane
 */
public class CanvasController {
    private Model model = null;

    @FXML
    ScrollPane canvasPane;

    @FXML
    Pane pane;

    @FXML
    Canvas canvas;

    GraphicsContext ctx = null;

    private int pixelSize = 4;

    private int currentDrawHeight = 0;

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();

        canvasPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> updateScrolling(canvasPane));
        canvasPane.hvalueProperty().addListener((observable, oldValue, newValue) -> updateScrolling(canvasPane));
        canvasPane.vvalueProperty().addListener((observable, oldValue, newValue) -> updateScrolling(canvasPane));

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

    private void updateScrolling(ScrollPane scrollPane){
        double hValue = scrollPane.getHvalue();
        double vValue = scrollPane.getVvalue();
        double portWidth = scrollPane.getViewportBounds().getWidth();
        double portHeight = scrollPane.getViewportBounds().getHeight();
        System.out.println("listening");
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
