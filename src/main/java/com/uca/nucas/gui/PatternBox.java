package com.uca.nucas.gui;

import com.uca.nucas.engine.ruleset.localrule.arbitraryrule.WPattern;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class PatternBox extends Pane {
    @FXML
    public AnchorPane PatternBoxRoot;
    @FXML
    public Button upButton;
    @FXML
    public Button downButton;
    @FXML
    public ScrollPane neighborsScrollPane;
    @FXML
    public HBox NeighborsBox;
    @FXML
    public Pane newStatePane;
    @FXML
    public Button deleteButton;

    int alphabetSize;
    int radius;
    int defo;
    int newState;
    ArrayList<Integer> states = new ArrayList<>();

    public void initialize() {

    }

    void populate() {
        for (int i = 0; i < 2 * radius + 1; i++) {
            states.add(-1);
            Pane pane = new Pane();
            pane.setMinSize(50, 50);
            pane.setMaxSize(50, 50);
            pane.setOnMouseClicked(mouseEvent -> {
                //TODO
            });
        }
    }

    WPattern getPattern() {
        return new WPattern(states, newState);
    }

    public void setAlphabetSize(int alphabetSize) {
        this.alphabetSize = alphabetSize;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setDefo(int defo) {
        this.defo = defo;
    }
}
