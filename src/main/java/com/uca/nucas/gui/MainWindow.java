/*
 * Developed by Matthias Percelay. Created on 14/03/19 00:53.
 * Last modified 14/03/19 00:53
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

/**
 * Main controller class for the gui
 */
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.uca.nucas.engine.Alphabet;
import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.distribution.UniformDistribution;
import com.uca.nucas.engine.ruleset.RuleSet;
import com.uca.nucas.engine.ruleset.SimpleRuleSet;
import com.uca.nucas.engine.ruleset.localrule.ElementaryRule;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class MainWindow {
    Automaton automaton;
    Configuration configuration;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="hostWindow"
    private BorderPane hostWindow; // Value injected by FXMLLoader

    @FXML // fx:id="OneStepButton"
    private Button oneStepButton; // Value injected by FXMLLoader

    @FXML
    void oneStepButtonAction(ActionEvent event) {
        //do stuff
    }

    @FXML // fx:id="HundredStepButton"
    private Button hundredStepButton; // Value injected by FXMLLoader

    @FXML // fx:id="submitButton"
    private Button submitButton; // Value injected by FXMLLoader

    @FXML
    void onSubmitButtonFired(ActionEvent event) {
        int code = Integer.parseInt(wolframCodeField.getText());
        buildAutomaton(code);
    }


    @FXML // fx:id="generateButton"
    private Button generateButton; // Value injected by FXMLLoader

    @FXML
    void onGenerateButtonFired(ActionEvent event) {
        
    }

    @FXML // fx:id="wolframCodeField"
    private TextField wolframCodeField; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="canvas"
    private Canvas canvas; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert hostWindow != null : "fx:id=\"hostWindow\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert oneStepButton != null : "fx:id=\"OneStepButton\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert hundredStepButton != null : "fx:id=\"HundredStepButton\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert wolframCodeField != null : "fx:id=\"wolframCodeField\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'mainwindow.fxml'.";
        assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'mainwindow.fxml'.";
    }

    void buildAutomaton(int code) {
        ElementaryRule rule = new ElementaryRule(code);
        RuleSet globalRule = new SimpleRuleSet(new LocalRule[] {rule});
        Distribution dist = new UniformDistribution(0);
        Alphabet alphabet = new Alphabet(new int[] {0, 1});
        alphabet.setColor(1, Color.BLACK);
        automaton = new Automaton(alphabet, globalRule, dist, 1);
    }
}