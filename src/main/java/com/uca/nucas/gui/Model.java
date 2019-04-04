package com.uca.nucas.gui;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.configuration.Configuration;
import javafx.concurrent.ScheduledService;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Model {
    private static Model modelInstance = null;

    private ArrayList<Configuration> spaceTimeDiagram;
    private Configuration startingConfiguration;
    private Automaton automaton;
    private int maxSteps;

    private Model(){}

    public static Model getModelInstance() {
        if (modelInstance == null) modelInstance = new Model();
        return modelInstance;
    }

    public void runAutomaton() {
        Configuration conf = startingConfiguration;
        for (int i = 0; i < maxSteps; i++) {
            conf = automaton.evaluate(conf);
            spaceTimeDiagram.add(conf);
        }
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public void setStartingConfiguration(Configuration startingConfiguration) {
        this.startingConfiguration = startingConfiguration;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public Color[] getColors(int i) {
        Color[] res = new Color[spaceTimeDiagram.get(0).getInitialSize()];
        for (int j = 0; j < spaceTimeDiagram.get(0).getInitialSize(); j++) {
            if (spaceTimeDiagram.get(i).getCell(j) == 1) res[j] = Color.BLACK;
            else res[j] = Color.WHITE;
        }
        return res;
    }
}
