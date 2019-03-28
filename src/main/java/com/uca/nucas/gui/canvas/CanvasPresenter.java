/*
 * Developed by Matthias Percelay. Created on 27/03/19 23:01.
 * Last modified 27/03/19 23:01
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui.canvas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasPresenter implements Initializable {

    @FXML
    ScrollPane canvasPane;

    @FXML
    Canvas canvas;

    private GraphicsContext ctx = null;
    private int currentDrawHeight = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctx = canvas.getGraphicsContext2D();
    }

    public void drawLine(int lineHeight, Color[] content) {
        PixelWriter pw = ctx.getPixelWriter();
        //PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbInstance();

        for (int i = 0; i < content.length; i++) {
            pw.setColor(i, lineHeight, content[i]);
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
}
