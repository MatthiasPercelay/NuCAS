package com.uca.nucas.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScrollExample2 extends Application {

    Canvas canvas = new Canvas( 400, 400);

    Pane pane = null;

    @Override
    public void start(Stage primaryStage) {

        pane = new Pane(canvas);
        pane.setMinSize(2000, 2000);
        pane.setMaxSize(2000, 2000);

        ScrollPane scrollPane = new ScrollPane( pane);
        scrollPane.setSnapToPixel(true);
        pane.setSnapToPixel(true);

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

        System.out.println( "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height);

        // demo: draw a line of the canvas size and a rectangle of the viewport size => the rectangle must always be in the center
        double size = 80;

        canvas.setWidth(width);
        canvas.setHeight(height);
        //canvas.relocate(x,y);
        canvas.relocate((double)Math.round(x), (double)Math.round(y));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(x, y, width, height);

        /*gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(canvas.getWidth(), canvas.getHeight());
        gc.closePath();
        gc.stroke();*/

        gc.fillRect((width-size) / 2, (height-size) / 2, size, size);

    }

    public static void main(String[] args) {
        launch(args);
    }

}