/*
 * Developed by Matthias Percelay. Created on 28/03/19 03:35.
 * Last modified 28/03/19 03:35
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui.leftpanel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftPanelPresenter implements Initializable {

    @FXML
    private TabPane leftPanel;

    @FXML
    private Tab elementaryTab;

    @FXML
    private TextField elementaryCodeField;

    @FXML
    private TextField configSizeField;

    @FXML
    private Button generateRandomButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
