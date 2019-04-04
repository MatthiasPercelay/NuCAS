/*
 * Developed by Matthias Percelay. Created on 27/03/19 18:57.
 * Last modified 14/03/19 12:43
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.gui;

/**
 * Main controller class for the gui
 */
import com.uca.nucas.engine.Alphabet;
import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.configuration.WrappingConfiguration;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.distribution.UniformDistribution;
import com.uca.nucas.engine.ruleset.localrule.ElementaryRule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class MainWindow {

    private int pixelSize = 4;

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
    public AnchorPane canvasPane;

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
        canvasPaneController.ctx = canvasPaneController.canvas.getGraphicsContext2D();
    }

    void buildAutomaton() {
        int code = Integer.parseInt(codeField.getText());
        ElementaryRule rule = new ElementaryRule(code);
        Distribution dist = new UniformDistribution(rule);
        Alphabet alphabet = new Alphabet(new int[] {0, 1});
        alphabet.setColor(1, Color.BLACK);
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
        Configuration conf = new WrappingConfiguration(contents);
        model.setStartingConfiguration(conf);
        System.out.println("Configuration added to model");
    }

    public void generateButtonFired() {
        buildAutomaton();
        buildConfiguration();
        model.setMaxSteps(Integer.parseInt(stepsField.getText()));
        canvasPaneController.setCanvasHeight(pixelSize * Integer.parseInt(stepsField.getText()));
        canvasPaneController.setCanvasWidth(pixelSize * Integer.parseInt(widthField.getText()));
        //canvasPaneController.clearCanvas();
    }

    public void runButtonFired() {
        model.runAutomaton();
        System.out.println("Automaton run");
        drawAutomaton();
        //canvasPaneController.ctx.setFill(Color.DARKBLUE);
        //canvasPaneController.ctx.fillRect(50,50,200,200);
        //canvasPaneController.ctx.drawImage(new Image("/exampleimage.png",400, 400,false,false),0,0);
    }

    public void drawAutomaton() {
        for (int i = 0; i < model.getMaxSteps(); i++) {
            canvasPaneController.drawLinePixelSize(i * pixelSize, model.getColors(i), pixelSize);
        }
    }
}