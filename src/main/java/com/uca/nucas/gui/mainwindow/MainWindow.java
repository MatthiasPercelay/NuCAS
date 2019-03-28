/*
 * Developed by Matthias Percelay. Created on 27/03/19 18:57.
 * Last modified 14/03/19 12:43
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui.mainwindow;

/**
 * Main controller class for the gui
 */
import com.uca.nucas.gui.canvas.CanvasView;
import com.uca.nucas.gui.leftpanel.LeftPanelView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindow implements Initializable {

    @FXML
    private BorderPane hostWindow;

    @FXML
    private VBox top;

    @FXML
    private AnchorPane bottom;

    @FXML
    private AnchorPane right;

    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane left;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CanvasView centerView = new CanvasView();
        center.getChildren().add(centerView.getView());
        LeftPanelView leftPanelView = new LeftPanelView();
        left.getChildren().add(leftPanelView.getView());
    }

    /*void buildAutomaton(int code) {
        ElementaryRule rule = new ElementaryRule(code);
        Distribution dist = new UniformDistribution(rule);
        Alphabet alphabet = new Alphabet(new int[] {0, 1});
        alphabet.setColor(1, Color.BLACK);
        automaton = new Automaton(alphabet, dist, 1);
    }*/
}