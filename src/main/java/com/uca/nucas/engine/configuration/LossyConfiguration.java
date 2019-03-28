/*
 * Developed by Matthias Percelay. Created on 05/03/19 02:38.
 * Last modified 05/03/19 02:38
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.distribution.Distribution;

/**
 * Configuration that shrinks each step
 */
public class LossyConfiguration extends AbstractConfiguration {
    public static final int LOSSY_CONF = 2;

    int startIndex;
    int endIndex;
    int initialSize;
    int lostState;

    public LossyConfiguration(int[] contents, int lostState) {
        super(contents);
        this.lostState = lostState;
        this.initialSize = contents.length;
        this.startIndex = 0;
        this.endIndex = this.initialSize;
    }

    LossyConfiguration(LossyConfiguration oldConf, int[] contents, Automaton automaton) {
        super(contents);
        this.lostState = oldConf.lostState;
        this.initialSize = oldConf.initialSize;
        this.startIndex = Math.min(initialSize, oldConf.startIndex + automaton.getRadius());
        this.endIndex = Math.max(0, oldConf.endIndex - automaton.getRadius());
    }

    @Override
    public int getCell(int index) {
        if (index >= startIndex && index < endIndex) return contents[index - startIndex];
        else return lostState;
    }

    @Override
    public void setCell(int index, int state) {
        contents[index] = state;
    }

    @Override
    public int getSize() {
        return Math.max(0, endIndex - startIndex);
    }

    @Override
    public int getInitialSize() {
        return initialSize;
    }

    @Override
    public int getStartPoint() {
        return startIndex;
    }

    @Override
    public Configuration accept(Automaton automaton) {
        return new LossyConfiguration(this, compute(automaton), automaton);
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
        Distribution dist = automaton.getDistribution();

        int[] res = new int[Math.max(0, end - start + 1)];

        for (int i = 0; i < res.length; i++) {
            res[i] = dist.getLocalRule(i + start).evaluate(i + start, this);
        }

        /*for (int i = 0; i < start; i++) {
            res[i] = lostState;
        }
        for (int i = start; i < end; i++) {
            res[i] = dist.getLocalRule(i).evaluate(i, this);
        }
        for (int i = end; i < getInitialSize(); i++) {
            res[i] = lostState;
        }*/

        return res;
    }
}
