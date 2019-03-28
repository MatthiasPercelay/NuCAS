/*
 * Developed by Matthias Percelay. Created on 27/03/19 19:25.
 * Last modified 27/03/19 18:59
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;


import com.uca.nucas.gui.mainwindow.MainWindowView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class
 */
public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainWindowView mainWindow = new MainWindowView();
        //Parent root = FXMLLoader.load(getClass().getResource("/mainwindow/mainwindow.fxml"));
        Scene scene = new Scene(mainWindow.getView());

        stage.setTitle("NuCAS");
        stage.setScene(scene);
        stage.show();
    }
}
