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
import com.uca.nucas.engine.alphabet.TernaryAlphabet;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.configuration.GrowingConfiguration;
import com.uca.nucas.engine.configuration.LossyConfiguration;
import com.uca.nucas.engine.configuration.WrappingConfiguration;
import com.uca.nucas.engine.distribution.DefaultBoundDistribution;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.distribution.UniformDistribution;
import com.uca.nucas.engine.ruleset.localrule.ElementaryRule;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import com.uca.nucas.engine.ruleset.localrule.perturbationexample.LeftGenRule;
import com.uca.nucas.engine.ruleset.localrule.perturbationexample.MainRule;
import com.uca.nucas.engine.ruleset.localrule.perturbationexample.RightGenRule;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Random;


public class MainWindowController {

    @FXML
    public ChoiceBox<String> pixelSizeBox;

    @FXML
    public Button generateButton;

    @FXML
    public Button perturbationButton;

    @FXML
    public TextField codeField;

    @FXML
    public TextField stepsField;

    @FXML
    public TextField widthField;

    @FXML
    public Button refreshButton;

    @FXML
    public ComboBox<String> stateSelectBox;

    @FXML
    public ColorPicker colorPicker;

    @FXML
    public ChoiceBox confModeBox;

    Model model = null;

    @FXML
    public AnchorPane center;

    @FXML
    public CanvasController canvasRegionController;

    @FXML
    private BorderPane hostWindow;

    @FXML
    VBox top;

    public void initialize() {
        model = Model.getModelInstance();
        canvasRegionController.setModel(model);

        hostWindow.widthProperty().addListener(this::updateWidth);

        hostWindow.heightProperty().addListener(this::updateHeight);

        pixelSizeBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    canvasRegionController.setPixelSize(Integer.parseInt(newValue));
                    canvasRegionController.clearConfCanvas();
                    int width = model.getSpaceTimeDiagram().getMaxConfSize();
                    int height = model.getMaxSteps();
                    canvasRegionController.setupScrollbars(width, height);
                    canvasRegionController.updateScrolling();
                });

        stateSelectBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                            model.setCurrentEditingState((int) newValue - 1);
                            try {
                                colorPicker.setValue(model.getStateColor(model.getCurrentEditingState()));
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                );

        colorPicker.setOnAction(actionEvent -> {
            try {
                int state = stateSelectBox.getSelectionModel().getSelectedIndex() - 1;
                model.getAutomaton().getAlphabet().setColor(state, colorPicker.getValue());
                canvasRegionController.updateScrolling();
            } catch (Exception e) {colorPicker.setValue(Color.WHITE);}
        });

        confModeBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (!oldValue.equals(newValue)) {
                        SpaceTimeDiagram std = model.getSpaceTimeDiagram();
                        Configuration newConf;
                        int[] contents = std.get(0).getContents();
                        if (newValue.equals("wrapping")) newConf = new WrappingConfiguration(contents);
                        else if (newValue.equals("growing")) newConf = new GrowingConfiguration(contents, 0);
                        else newConf = new LossyConfiguration(contents, -1);
                        std.setStartingConfiguration(newConf);

                        canvasRegionController.clearDistCanvas();
                        canvasRegionController.clearConfCanvas();
                        model.resetToStart();
                        model.runAutomaton();
                        canvasRegionController.updateScrolling();
                    }
                }));

        /**
         * event handler that finalises setup the first time the user interacts with the program, then removes itself
         */
        hostWindow.addEventHandler(Event.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                updateHeight();
                updateWidth();
                hostWindow.removeEventHandler(Event.ANY, this);
            }
        });
    }

    /**
     * builds an elementary automaton
     */
    void buildAutomaton() {
        int code = Integer.parseInt(codeField.getText());
        ElementaryRule rule = new ElementaryRule(code);
        Distribution dist = new UniformDistribution(rule);
        Alphabet alphabet = new BinaryAlphabet();
        Automaton automaton = new Automaton(alphabet, dist, 1);

        model.setAutomaton(automaton);
        System.out.println("Automaton added to model");
    }

    /**
     * buids a randomised configuration with alphabet size noStates
     * other parameters acquired from UI controls
     *
     * @param noStates size of the alphabet
     */
    void buildConfiguration(int noStates) {
        int[] contents = new int[Integer.parseInt(widthField.getText())];
        Random rand = new Random();
        for (int i = 0; i < contents.length; i++) {
            contents[i] = rand.nextInt(noStates);
        }

        Configuration conf;
        if (confModeBox.getValue().equals("wrapping")) conf = new WrappingConfiguration(contents);
        else if (confModeBox.getValue().equals("growing")) conf = new GrowingConfiguration(contents, 0);
        else conf = new LossyConfiguration(contents, -1);
        model.getSpaceTimeDiagram().setStartingConfiguration(conf);
        System.out.println("Configuration added to model");
    }

    /**
     * performs setup for the simulation and the display from UI controls
     */
    void setupModelAndCanvas() {
        stateSelectBox.setItems(FXCollections.observableList(model.getAutomaton().getAlphabet().getStateNames()));

        model.setMaxSteps(Integer.parseInt(stepsField.getText()));
        model.resetToStart();
        int width = model.getSpaceTimeDiagram().getMaxConfSize();
        int height = model.getMaxSteps();
        canvasRegionController.setupScrollbars(width, height);
        canvasRegionController.clearConfCanvas();
    }

    /**
     * builds an elementary automaton and runs it
     */
    public void generateButtonFired() {
        buildAutomaton();
        buildConfiguration(2);
        setupModelAndCanvas();
        refreshButtonFired();
    }

    /**
     * refresh the simulation
     */
    public void refreshButtonFired() {
        model.resetToStart();
        model.runAutomaton();
        canvasRegionController.clearConfCanvas();
        canvasRegionController.updateScrolling();
        System.out.println("Automaton run");
    }

    public void stepButtonFired() {
        model.runOneStep();
    }

    /**
     * builds a perturbation model and runs it
     */
    public void perturbationButtonFired() {
        LocalRule[] distData = new LocalRule[Integer.parseInt(widthField.getText())];
        for (int i = 0; i < distData.length; i++) {
            distData[i] = new MainRule();
        }
        distData[distData.length / 2 - 2] = new LeftGenRule();
        distData[distData.length / 2] = new RightGenRule();
        Distribution dist = new DefaultBoundDistribution(distData, new MainRule());
        Alphabet alphabet = new TernaryAlphabet();
        Automaton automaton = new Automaton(alphabet, dist, 1);
        model.setAutomaton(automaton);

        buildConfiguration(3);
        setupModelAndCanvas();
        refreshButtonFired();
    }

    private void updateWidth(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        updateWidth();
    }

    /**
     * update the width of the canvas region according to overall dimensions
     */
    private void updateWidth() {
        canvasRegionController.canvasRegion.setMinWidth(hostWindow.getWidth());
        canvasRegionController.canvasRegion.setMaxWidth(hostWindow.getWidth());
    }

    private void updateHeight(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        updateHeight();
    }

    /**
     * update the height of the canvas region according to overall dimensions
     */
    private void updateHeight() {
        canvasRegionController.canvasRegion.setMinHeight(hostWindow.getHeight() - top.getHeight());
        canvasRegionController.canvasRegion.setMaxHeight(hostWindow.getHeight() - top.getHeight());
    }
}