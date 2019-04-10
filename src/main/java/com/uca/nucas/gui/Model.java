package com.uca.nucas.gui;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.configuration.Configuration;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Singleton in charge of managing the model of the simulation
 * TODO : make it async because it currently blocks the UI thread
 * TODO : add support for the other configuration types
 */
public class Model {
    /**
     * Singleton instance
     */
    private static Model modelInstance = null;

    /**
     * holds the space time diagram generated by the simulation
     */
    private ArrayList<Configuration> spaceTimeDiagram = new ArrayList<>();

    /**
     * the automaton to use
     */
    private Automaton automaton;

    /**
     * the maximum number of steps for the simulation
     * may be removed at a later date if a convenient way to run a simulation
     * for a variable amount of steps is needed
     */
    private int maxSteps;

    private Model(){}

    /**
     * getter for the Model instance
     * @return
     */
    public static Model getModelInstance() {
        if (modelInstance == null) modelInstance = new Model();
        return modelInstance;
    }

    /**
     * runs the automaton to the limit of maxSteps
     */
    public void runAutomaton() {
        Configuration conf = spaceTimeDiagram.get(0);
        for (int i = 0; i < maxSteps; i++) {
            conf = automaton.evaluate(conf);
            spaceTimeDiagram.add(conf);
        }
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public Color getStateColor(int state) {
        return automaton.getAlphabet().getColor(state);
    }

    public int getConfigurationStartingSize() {
        if (spaceTimeDiagram.size() == 0) return 0;
        return spaceTimeDiagram.get(0).getInitialSize();
    }

    /**
     * clears the space-time diagram except for the starting configuration
     */
    public void clearSTDiagram() {
        Configuration startingConfiguration = spaceTimeDiagram.get(0);
        spaceTimeDiagram = new ArrayList<>();
        spaceTimeDiagram.add(startingConfiguration);
    }

    public void setStartingConfiguration(Configuration startingConfiguration) {
        if (spaceTimeDiagram.size() == 0) {
            spaceTimeDiagram.add(startingConfiguration);
        } else {
            spaceTimeDiagram.set(0, startingConfiguration);
        }
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public int getCurrentSteps() {
        return spaceTimeDiagram.size();
    }

    /**
     * obtain an array of Color objects corresponding to the i-th configuration in the space-time diagram
     * TODO : make it use the Alphabet properly instead of being limited to binary alphabets
     * @param i
     * @return
     */
    public Color[] getColors(int i) {
        Color[] res = new Color[spaceTimeDiagram.get(0).getInitialSize()];
        for (int j = 0; j < spaceTimeDiagram.get(0).getInitialSize(); j++) {
            if (spaceTimeDiagram.get(i).getCell(j) == 1) res[j] = Color.BLACK;
            else res[j] = Color.WHITE;
        }
        return res;
    }

    public int[][] getSTDiagramView(int startIndex, int endIndex, int startStep, int endStep) {
        int[][] res = new int[endStep - startStep][endIndex - startIndex];
        for (int i = startStep; i < endStep; i++) {
            for (int j = startIndex; j < endIndex; j++) {
                res[i][j] = spaceTimeDiagram.get(i).getCell(j);
            }
        }
        return res;
    }

    public int[] getSTDiagramSegment(int startIndex, int endIndex, int step) {
        Configuration configuration = spaceTimeDiagram.get(step);
        startIndex = Math.min(startIndex, configuration.getGreatestSize());
        endIndex = Math.min(endIndex, configuration.getGreatestSize());
        int[] res = new int[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; i++) {
            res[i] = configuration.getCell(i);
        }
        return res;
    }
}
