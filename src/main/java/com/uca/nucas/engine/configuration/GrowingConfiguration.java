/*
 * Developed by Matthias Percelay. Created on 04/03/19 20:58.
 * Last modified 04/03/19 20:28
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.distribution.Distribution;

/**
 * Configuration that grows each step to maintain perfect information
 * the working area is surrounded on both sides by an infinitely repeating default state
 */
public class GrowingConfiguration extends AbstractConfiguration {
    public static final int GROWING_CONF = 1;

    private int defaultState;
    private int originOffset;
    private int originalSize;

    public GrowingConfiguration(int[] contents, int defaultState) {
        super(contents);
        this.defaultState = defaultState;
        this.originalSize = contents.length;
        this.originOffset = 0;
    }

    GrowingConfiguration(GrowingConfiguration oldConf, int[] contents, Automaton automaton) {
        super(contents);
        this.defaultState = oldConf.defaultState;
        this.originOffset = oldConf.originOffset + automaton.getRadius();
        this.originalSize = oldConf.originalSize;
    }

    @Override
    public int getCell(int index) {
        if (index < 0 - originOffset || index >= contents.length - originOffset) return defaultState;
        else return contents[index + originOffset];
    }

    @Override
    public void setCell(int index, int state) {
        contents[index + originOffset] = state;
    }

    @Override
    public int getInitialSize() {
        return originalSize;
    }

    @Override
    public int getDistributionOffset() {
        return originOffset;
    }

    @Override
    public GrowingConfiguration accept(Automaton automaton) {
        return new GrowingConfiguration(this, compute(automaton), automaton);
    }

    int[] compute(Automaton automaton) {
        int radius = automaton.getRadius();
        int offset = getDistributionOffset() + radius;
        Distribution dist = automaton.getDistribution();

        int[] res = new int[getSize() + 2 * radius];

        for(int i = 0; i < res.length; i++) {
            res[i] = dist.getLocalRule(i - offset).evaluate(i - offset, this);
        }
        return res;
    }
}
