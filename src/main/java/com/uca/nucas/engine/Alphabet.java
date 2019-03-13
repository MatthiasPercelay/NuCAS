/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:21.
 * Last modified 28/02/19 16:21
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

import java.util.HashSet;

/**
 * represents an alphabet over which automata are defined
 */
public class Alphabet {
    HashSet<Integer> states;

    public Alphabet(int[] states) {
        this.states = new HashSet<Integer>();
        for (int i = 0; i < states.length; i++) {
            this.states.add(states[i]);
        }
    }
}
