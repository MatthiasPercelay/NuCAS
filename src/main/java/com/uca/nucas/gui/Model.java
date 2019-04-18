package com.uca.nucas.gui;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import javafx.scene.paint.Color;

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
    private SpaceTimeDiagram spaceTimeDiagram = new SpaceTimeDiagram();

    /**
     * the automaton to use
     */
    private Automaton automaton;

    /**
     * the maximum number of steps for the simulation
     * may be removed at a later date if a convenient way to run a simulation
     * for a variable amount of steps is needed
     */
    private int maxSteps = 0;

    private int currentSteps = 0;

    /**
     * indicates whether or not the current automaton has started its simulation
     */
    private boolean hasRun = false;

    private int currentEditingState = 0;

    private Model(){}

    /**
     * getter for the Model instance
     * @return
     */
    public static Model getModelInstance() {
        if (modelInstance == null) modelInstance = new Model();
        return modelInstance;
    }

    public boolean hasRun() {
        return hasRun;
    }

    /**
     * runs the automaton to the limit of maxSteps
     */
    public void runAutomaton() {
        Configuration conf = spaceTimeDiagram.get(0);
        for (int i = currentSteps; i < maxSteps; i++) {
            conf = automaton.evaluate(conf);
            spaceTimeDiagram.append(conf);
            currentSteps = i + 1;
        }
        hasRun = true;
    }

    public void runOneStep() {
        Configuration conf = automaton.evaluate(spaceTimeDiagram.getLast());
        currentSteps++;
        hasRun = true;
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public LocalRule[] getArrayOfRules(int start, int end) {
        Distribution dist = automaton.getDistribution();
        LocalRule[] res = new LocalRule[end - start];
        for (int i = 0; i < end - start; i++) {
            res[i] = dist.getLocalRule(i + start);
        }
        return res;
    }

    public SpaceTimeDiagram getSpaceTimeDiagram() {
        return spaceTimeDiagram;
    }

    public int getCurrentEditingState() {
        return currentEditingState;
    }

    public void setCurrentEditingState(int currentEditingState) {
        this.currentEditingState = currentEditingState;
    }

    public void resetToStart() {
        spaceTimeDiagram.clearComputations();
        hasRun = false;
        currentSteps = 0;
    }

    public Color getStateColor(int state) {
        return automaton.getAlphabet().getColor(state);
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public int getCurrentSteps() {
        return spaceTimeDiagram.getConfCount();
    }
}
