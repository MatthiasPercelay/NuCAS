/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:27.
 * Last modified 28/02/19 16:27
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

import com.uca.nucas.engine.alphabet.Alphabet;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.distribution.Distribution;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Automaton {
    Alphabet alphabet;
    Distribution dist;
    int radius;

    HashMap<LocalRule, Color> ruleColors;

    public Automaton(Alphabet alphabet, Distribution dist, int radius) {
        this.alphabet = alphabet;
        this.dist = dist;
        this.radius = radius;

        ruleColors = new HashMap<>();
        var rules = dist.getSetOfRules();
        int count = rules.size();
        double i = 0;
        for (var rule : rules) {
            ruleColors.put(rule, Color.WHITE.interpolate(Color.BLACK, i / count));
            i++;
        }
    }

    public Configuration evaluate(Configuration conf) {
        return conf.accept(this);
    }

    public Color getRuleColor(LocalRule rule) {
        return ruleColors.get(rule);
    }

    public void setRuleColor(LocalRule rule, Color color) {
        ruleColors.replace(rule, color);
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
