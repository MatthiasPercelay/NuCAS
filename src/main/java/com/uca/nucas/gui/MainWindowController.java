/*
 * Developed by Matthias Percelay. Created on 27/03/19 18:57.
 * Last modified 14/03/19 12:43
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

/**
 * Main controller class for the gui
 */
import com.uca.nucas.engine.alphabet.Alphabet;
import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.alphabet.BinaryAlphabet;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.configuration.GrowingConfiguration;
import com.uca.nucas.engine.configuration.LossyConfiguration;
import com.uca.nucas.engine.configuration.WrappingConfiguration;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.distribution.UniformDistribution;
import com.uca.nucas.engine.ruleset.localrule.ElementaryRule;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.Random;


public class MainWindowController {

    @FXML
    public ChoiceBox<String> pixelSizeBox;

    @FXML
    public Button generateButton;

    @FXML
    public TextField codeField;

    @FXML
    public TextField stepsField;

    @FXML
    public TextField widthField;

    @FXML
    public Button runButton;

    Model model = null;

    @FXML
    public ScrollPane canvasPane;

    @FXML
    public CanvasController canvasPaneController;

    @FXML
    private BorderPane hostWindow;

    @FXML
    private AnchorPane bottom;

    @FXML
    private AnchorPane right;

    @FXML
    private AnchorPane left;

    public void initialize() {
        model = Model.getModelInstance();
        canvasPaneController.setModel(model);

        pixelSizeBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    canvasPaneController.setPixelSize(Integer.parseInt(newValue));
                    canvasPaneController.clearCanvas();
                    int width = model.getSpaceTimeDiagram().getMaxConfSize();
                    int height = model.getMaxSteps();
                    canvasPaneController.setSizePaneDims(width, height, canvasPaneController.getPixelSize());
                });
    }

    void buildAutomaton() {
        int code = Integer.parseInt(codeField.getText());
        ElementaryRule rule = new ElementaryRule(code);
        Distribution dist = new UniformDistribution(rule);
        Alphabet alphabet = new BinaryAlphabet();
        Automaton automaton = new Automaton(alphabet, dist, 1);

        model.setAutomaton(automaton);
        System.out.println("Automaton added to model");
    }

    void buildConfiguration() {
        int[] contents = new int[Integer.parseInt(widthField.getText())];
        Random rand = new Random();
        for (int i = 0; i < contents.length; i++) {
            contents[i] = rand.nextInt(2);
        }
        //Configuration conf = new WrappingConfiguration(contents);
        Configuration conf = new GrowingConfiguration(contents, 0);
        //Configuration conf = new LossyConfiguration(contents, -1);
        model.getSpaceTimeDiagram().setStartingConfiguration(conf);
        System.out.println("Configuration added to model");
    }

    public void generateButtonFired() {
        buildAutomaton();
        buildConfiguration();
        model.setMaxSteps(Integer.parseInt(stepsField.getText()));
        model.resetToStart();
        int width = model.getSpaceTimeDiagram().getMaxConfSize();
        int height = model.getMaxSteps();
        canvasPaneController.setSizePaneDims(width, height, canvasPaneController.getPixelSize());
        canvasPaneController.clearCanvas();
    }

    public void runButtonFired() {
        model.resetToStart();
        model.runAutomaton();
        canvasPaneController.clearCanvas();
        canvasPaneController.updateScrolling();
        System.out.println("Automaton run");
    }


}