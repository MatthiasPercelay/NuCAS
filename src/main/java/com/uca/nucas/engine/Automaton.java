/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:27.
 * Last modified 28/02/19 16:27
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

import com.uca.nucas.engine.alphabet.Alphabet;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.distribution.Distribution;

public class Automaton {
    Alphabet alphabet;
    Distribution dist;
    int radius;

    public Automaton(Alphabet alphabet, Distribution dist, int radius) {
        this.alphabet = alphabet;
        this.dist = dist;
        this.radius = radius;
    }

    public Configuration evaluate(Configuration conf) {
        return conf.accept(this);
    }

    public Distribution getDistribution() {
        return dist;
    }

    public int getRadius() {
        return radius;
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }
}
