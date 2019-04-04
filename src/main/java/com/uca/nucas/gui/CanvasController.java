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
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController {

    private Model model = null;

    @FXML
    AnchorPane canvasPane;

    @FXML
    Canvas canvas;

    public GraphicsContext ctx = null;
    private int currentDrawHeight = 0;

    public void drawLine(int lineHeight, Color[] content) {
        PixelWriter pw = ctx.getPixelWriter();

        for (int i = 0; i < content.length; i++) {
            pw.setColor(i, lineHeight, content[i]);
        }
    }

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

    public void ping() {
        System.out.println("canvas controller pinged");
    }
}
