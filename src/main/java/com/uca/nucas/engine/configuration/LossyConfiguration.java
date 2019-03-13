/*
 * Developed by Matthias Percelay. Created on 05/03/19 02:38.
 * Last modified 05/03/19 02:38
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.ruleset.RuleSet;

/**
 * Configuration that shrinks each step
 */
public class LossyConfiguration extends AbstractConfiguration {
    public static final int LOSSY_CONF = 2;

    int startIndex;
    int usefulSize;
    int lostState;

    public LossyConfiguration(int[] contents, int lostState) {
        super(contents);
        this.lostState = lostState;
        this.usefulSize = contents.length;
        this.startIndex = 0;
    }

    LossyConfiguration(LossyConfiguration oldConf, int[] contents, Automaton automaton) {
        super(contents);
        this.lostState = oldConf.lostState;
        this.startIndex = oldConf.startIndex + automaton.getRadius();
        this.usefulSize = oldConf.usefulSize - 2 * automaton.getRadius();
    }

    LossyConfiguration(int[] contents, int startIndex, int usefulSize, int lostState) {
        super(contents);
        this.startIndex = startIndex;
        this.usefulSize = usefulSize;
        this.lostState = lostState;
    }

    @Override
    public int getCell(int index) {
        return contents[index];
    }

    @Override
    public void setCell(int index, int state) {
        contents[index] = state;
    }

    @Override
    public int getSize() {
        return usefulSize;
    }

    @Override
    public int getInitialSize() {
        return contents.length;
    }

    @Override
    public int getStartPoint() {
        return startIndex;
    }

    @Override
    public Configuration accept(Automaton automaton) {
        return new LossyConfiguration(this, compute(automaton), automaton);
        //return new LossyConfiguration(compute(automaton), startIndex + 1, usefulSize - 2, lostState);
    }

    private int[] shrink(int[] contents, int radius) {
        int size = getInitialSize();
        int[] res = new int[size];

        for(int i = 0; i < startIndex + radius; i++) {
            res[i] = lostState;
            res[size - i] = lostState;
        }
        for (int j = 0; j < contents.length; j++) {
            res[j + radius] = contents[j];
        }
        return res;
    }

    @Override
    int[] compute(Automaton automaton) {
        int radius = automaton.getRadius();
        int start = getStartPoint() + radius;
        int end = start + getSize() - 2 * radius;
        RuleSet globalRule = automaton.getRuleSet();
        Distribution dist = automaton.getDistribution();

        int[] res = new int[getInitialSize()];

        for (int i = 0; i < start; i++) {
            res[i] = lostState;
        }
        for (int i = start; i < end; i++) {
            res[i] = globalRule.callRule(dist.getLocalRule(i), i, this);
        }
        for (int i = end; i < getInitialSize(); i++) {
            res[i] = lostState;
        }
        return res;
    }
}
