/*
 * Developed by Matthias Percelay. Created on 04/03/19 20:59.
 * Last modified 04/03/19 20:59
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.Automaton;

/**
 * Configuration that forms a torus
 */
public class WrappingConfiguration extends AbstractConfiguration {
    public static final int WRAPPING_CONF = 3;

    public WrappingConfiguration(int[] contents) {
        super(contents);
    }

    public WrappingConfiguration(int size) {
        super(new int[size]);
    }

    @Override
    public int getCell(int index) {
        index %= contents.length;
        if (index < 0) index += contents.length;
        return contents[index];
    }

    @Override
    public void setCell(int index, int state) {
        index %= contents.length;
        if (index < 0) index += contents.length;
        contents[index] = state;
    }

    @Override
    public Configuration accept(Automaton automaton) {
        return new WrappingConfiguration(compute(automaton));
    }
}
