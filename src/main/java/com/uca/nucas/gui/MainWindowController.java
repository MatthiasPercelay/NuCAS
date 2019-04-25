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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
    public Canvas stateColorSwatch;

    @FXML
    public ChoiceBox confModeBox;

    Model model = null;

    @FXML
    public AnchorPane center;

    @FXML
    public CanvasController canvasPaneController;

    @FXML
    private BorderPane hostWindow;

    @FXML
    VBox top;

    public void initialize() {
        model = Model.getModelInstance();
        canvasPaneController.setModel(model);

        hostWindow.widthProperty().addListener(this::updateWidth);

        hostWindow.heightProperty().addListener(this::updateHeight);

        pixelSizeBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    canvasPaneController.setPixelSize(Integer.parseInt(newValue));
                    canvasPaneController.clearConfCanvas();
                    int width = model.getSpaceTimeDiagram().getMaxConfSize();
                    int height = model.getMaxSteps();
                    canvasPaneController.setupScrollbars(width, height);
                    canvasPaneController.updateScrolling();
                });

        stateSelectBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observableValue, number, t1) -> {
                    model.setCurrentEditingState((int)t1 - 1);
                    GraphicsContext gc = stateColorSwatch.getGraphicsContext2D();
                    Paint fill;
                    try {
                        fill = model.getStateColor(model.getCurrentEditingState());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        fill = Color.color(0, 0, 0, 0);
                    }
                    gc.setFill(fill);
                    gc.fillRect(0, 0, 25, 25);
                }
                );

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

                        canvasPaneController.clearDistCanvas();
                        canvasPaneController.clearConfCanvas();
                        model.resetToStart();
                        model.runAutomaton();
                        canvasPaneController.updateScrolling();
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

    void buildAutomaton() {
        int code = Integer.parseInt(codeField.getText());
        ElementaryRule rule = new ElementaryRule(code);
        Distribution dist = new UniformDistribution(rule);
        Alphabet alphabet = new BinaryAlphabet();
        Automaton automaton = new Automaton(alphabet, dist, 1);

        model.setAutomaton(automaton);
        System.out.println("Automaton added to model");
    }

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

    void setupModelAndCanvas() {
        stateSelectBox.setItems(FXCollections.observableList(model.getAutomaton().getAlphabet().getStateNames()));

        model.setMaxSteps(Integer.parseInt(stepsField.getText()));
        model.resetToStart();
        int width = model.getSpaceTimeDiagram().getMaxConfSize();
        int height = model.getMaxSteps();
        canvasPaneController.setupScrollbars(width, height);
        canvasPaneController.clearConfCanvas();
    }

    public void generateButtonFired() {
        buildAutomaton();
        buildConfiguration(2);
        setupModelAndCanvas();
        refreshButtonFired();
    }

    public void refreshButtonFired() {
        model.resetToStart();
        model.runAutomaton();
        canvasPaneController.clearConfCanvas();
        canvasPaneController.updateScrolling();
        System.out.println("Automaton run");
    }

    public void stepButtonFired() {
        model.runOneStep();
    }

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

    private void updateWidth() {
        canvasPaneController.canvasPane.setMinWidth(hostWindow.getWidth());
        canvasPaneController.canvasPane.setMaxWidth(hostWindow.getWidth());
    }

    private void updateHeight(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        updateHeight();
    }

    private void updateHeight() {
        canvasPaneController.canvasPane.setMinHeight(hostWindow.getHeight() - top.getHeight());
        canvasPaneController.canvasPane.setMaxHeight(hostWindow.getHeight() - top.getHeight());
    }
}