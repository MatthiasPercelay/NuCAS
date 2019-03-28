/*
 * Developed by Matthias Percelay. Created on 27/03/19 19:25.
 * Last modified 27/03/19 18:59
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class
 */
public class MainApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("NuCAS");
        stage.setScene(scene);
        stage.show();
    }
}
