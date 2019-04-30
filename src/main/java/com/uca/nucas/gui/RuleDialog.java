package com.uca.nucas.gui;

import com.uca.nucas.engine.ruleset.localrule.arbitraryrule.WPattern;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class RuleDialog extends DialogPane {
    @FXML
    public TextField alphabetField;
    @FXML
    public TextField radiusField;
    @FXML
    public TextField defaultField;
    @FXML
    public Button addPatternButton;
    @FXML
    public ScrollPane patternsPane;
    @FXML
    public VBox patternsBox;
    @FXML
    public DialogPane root;

    int alphabetSize;
    int radius;
    int defo;
    ArrayList<WPattern> patterns;

    @SuppressWarnings("Duplicates")
    public void initialize() {
        alphabetField.textProperty().addListener(((obervable, oldString, newString) -> {
            try {
                alphabetSize = Integer.parseInt(newString);
            } catch (Exception e) {
                alphabetField.textProperty().setValue("");
            }
        }));

        radiusField.textProperty().addListener(((observable, oldString, newString) -> {
            try {
                radius = Integer.parseInt(newString);
            } catch (Exception e) {
                radiusField.textProperty().setValue("");
            }
        }));

        defaultField.textProperty().addListener(((observable, oldString, newString) -> {
            try {
                defo = Integer.parseInt(newString);
            } catch (Exception e) {
                defaultField.textProperty().setValue("");
            }
        }));

        addPatternButton.setOnAction(actionEvent -> {
            PatternBox newBox = new PatternBox();
            newBox.setAlphabetSize(alphabetSize);
            newBox.setRadius(radius);
            newBox.setDefo(defo);
            newBox.populate();

            patternsBox.getChildren().add(newBox);
        });
    }

    public int[] neighbors() {
        int[] res = new int[2 * radius + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = i - radius;
        }
        return res;
    }

    public List<WPattern> collectPatterns() {
        List<WPattern> res = new ArrayList<>();
        for (Node node : patternsBox.getChildren()) {
            PatternBox pBox = (PatternBox)node;
            res.add(pBox.getPattern());
        }
        return res;
    }
}
