/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:21.
 * Last modified 28/02/19 16:21
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * represents an alphabet over which automata are defined
 * complete WIP, expect breaking changes
 */
public class Alphabet {
    HashSet<Integer> states;
    HashMap<Integer, Color> colors;

    public Alphabet(int[] states) {
        this.states = new HashSet<Integer>();
        this.colors = new HashMap<>();
        for (int i = 0; i < states.length; i++) {
            this.states.add(states[i]);
            this.colors.put(states[i], Color.WHITE);
        }
    }

    public Color getColor(int state) {
        if (colors.containsKey(state)) {
            return colors.get(state);
        } else {
            return Color.DARK_GRAY;
        }
    }

    public void setColor(int state, Color color) {
        if (states.contains(state)) {
            colors.replace(state, color);
        }
    }
}
