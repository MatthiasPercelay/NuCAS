/*
 * Developed by Matthias Percelay. Created on 11/03/19 07:52.
 * Last modified 11/03/19 07:52
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.ruleset.localrule;

import com.uca.nucas.engine.configuration.Configuration;

import java.util.Arrays;

public class ElementaryRule implements LocalRule {
    int[] transitions;

    public ElementaryRule(int code) {
        if (code < 0 || code > 255) throw new IllegalArgumentException("Rule number must be between 0 and 255");
        transitions = new int[8];
        for (int i = 0; i < 8; i++) {
            if ((code & 1 << i) != 0) transitions[i] = 1;
            else transitions[i] = 0;
        }
    }

    @Override
    public int evaluate(int position, Configuration conf) {
        int index = 0;
        if (conf.getRelativeCell(position, -1) == 1) index += 4;
        if (conf.getRelativeCell(position, 0) == 1) index += 2;
        if (conf.getRelativeCell(position, 1) == 1) index += 1;
        return transitions[index];
    }

    @Override
    public int getRadius() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementaryRule that = (ElementaryRule) o;
        return Arrays.equals(transitions, that.transitions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(transitions);
    }
}
