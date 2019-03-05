/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:27.
 * Last modified 28/02/19 16:27
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.distribution.Distribution;

public class Automaton {
    Alphabet alphabet;
    RuleSet ruleSet;

    public Automaton(Alphabet alphabet, RuleSet ruleSet) {
        this.alphabet = alphabet;
        this.ruleSet = ruleSet;
    }

    /*public Configuration evaluate(Configuration conf, Distribution dist) {
        int[] res = new int[conf.getSize()];

        for (int i = 0; i < conf.getSize(); i++) {
            res[i] = ruleSet.callRule(dist.get(i), i, conf);
        }

        return new Configuration(res);
    }*/
}
