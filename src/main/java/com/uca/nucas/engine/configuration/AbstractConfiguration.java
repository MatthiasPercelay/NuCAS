/*
 * Developed by Matthias Percelay. Created on 07/03/19 20:24.
 * Last modified 07/03/19 20:24
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.Automaton;

public abstract class AbstractConfiguration implements Configuration {
    int[] contents;

    public AbstractConfiguration(int[] contents) {
        this.contents = contents;
    }

    @Override
    public abstract int getCell(int index);

    @Override
    public abstract void setCell(int index, int state);

    @Override
    public int getSize() {
        return contents.length;
    }

    @Override
    public int getInitialSize() {
        return getSize();
    }

    @Override
    public int getStartPoint() {
        return 0;
    }

    @Override
    public int getDistributionOffset() {
        return 0;
    }

    @Override
    public abstract Configuration accept(Automaton automaton);

    /**
     * The way to calculate the next state of the simulation is the same for all configurations based on this class,
     * except for the handling of configuration growth/shrinkage and configuration metadata. This method extracts the
     * core computation logic such that child classes only need to handle the relevant details
     * @param automaton the automaton for which we compute the next array of states making up the configuration
     * @return an array of states to be wrapped in a Configuration
     */
    int[] compute(Automaton automaton) {
        int[] res = new int[this.getSize()];

        int end = getStartPoint() + getSize();
        int ruleOffset = getDistributionOffset();

        for (int i = getStartPoint(); i < end; i++) {
            res[i] = automaton.getDistribution().getLocalRule(i - ruleOffset).evaluate(i, this);
        }

        return res;
    }
}
