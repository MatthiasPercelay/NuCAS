package com.uca.nucas.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ScrollExample extends Application {

    Canvas canvas = new Canvas( 2000, 2000);

    @Override
    public void start(Stage primaryStage) {

        ScrollPane scrollPane = new ScrollPane( canvas);

        scrollPane.viewportBoundsProperty().addListener((ChangeListener<Bounds>) (observable, oldValue, newValue) -> showBounds( scrollPane));
        scrollPane.hvalueProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> showBounds( scrollPane));
        scrollPane.vvalueProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> showBounds( scrollPane));

        Scene scene = new Scene( scrollPane, 400, 400);

        primaryStage.setScene( scene);
        primaryStage.show();

    }

    private void showBounds( ScrollPane scrollPane) {

        double hValue = scrollPane.getHvalue();
        double vValue = scrollPane.getVvalue();
        double width = scrollPane.viewportBoundsProperty().get().getWidth();
        double height = scrollPane.viewportBoundsProperty().get().getHeight();

        double x = (scrollPane.getContent().getBoundsInParent().getWidth() - width) * hValue;
        double y = (scrollPane.getContent().getBoundsInParent().getHeight() - height) * vValue;

        //System.out.println( "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height);

        // demo: draw a line of the canvas size and a rectangle of the viewport size => the rectangle must always be in the center
        double size = 80;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(x, y, width, height);

        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(canvas.getWidth(), canvas.getHeight());
        gc.closePath();
        gc.stroke();

        gc.fillRect(x + (width-size) / 2, y + (height-size) / 2, size, size);

    }

    public static void main(String[] args) {
        launch(args);
    }

}