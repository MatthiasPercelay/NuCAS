/*
 * Developed by Matthias Percelay. Created on 27/03/19 18:57.
 * Last modified 14/03/19 02:20
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui.main;


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
