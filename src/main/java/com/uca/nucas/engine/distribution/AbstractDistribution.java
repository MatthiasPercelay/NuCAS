/*
 * Developed by Matthias Percelay. Created on 27/03/19 18:37.
 * Last modified 27/03/19 18:37
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;

public abstract class AbstractDistribution implements Distribution{
    LocalRule[] localRules;

    public AbstractDistribution(LocalRule[] localRules) {
        this.localRules = localRules;
    }

    @Override
    public abstract LocalRule getLocalRule(int index);

    @Override
    public int getRadius() {
        int max = 0;
        for (int i = 0; i < localRules.length; i++) {
            int current = localRules[i].getRadius();
            if (current > max) max = current;
        }
        return max;
    }

    @Override
    public void setLocalRule(int index, LocalRule rule) throws UnsupportedOperationException {
        if (index < 0 || index >= localRules.length) {
            throw new UnsupportedOperationException("Modifying rules beyond set active area not supported at this time");
        } else {
            localRules[index] = rule;
        }
    }
}
