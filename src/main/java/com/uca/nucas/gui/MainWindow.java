/*
 * Developed by Matthias Percelay. Created on 14/03/19 00:53.
 * Last modified 14/03/19 00:53
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

/**
 * Main controller class for the gui
 */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;


public class MainWindow {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="hostWindow"
    private BorderPane hostWindow; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="canvas"
    private Canvas canvas; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert hostWindow != null : "fx:id=\"hostWindow\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'mainwindow.fxml'.";

    }
}