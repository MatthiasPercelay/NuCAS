/*
 * Developed by Matthias Percelay. Created on 06/03/19 16:21.
 * Last modified 06/03/19 16:21
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;

/**
 * Distribution bound by a different periodic region at each end
 * => pnu-ca
 */
public class PeriodicBoundDistribution extends AbstractDistribution{
    LocalRule[] leftPattern;
    LocalRule[] rightPattern;

    public PeriodicBoundDistribution(LocalRule[] centerRules, LocalRule[] leftPattern, LocalRule[] rightPattern) {
        super(centerRules);
        this.leftPattern = leftPattern.clone();
        this.rightPattern = rightPattern.clone();
    }

    @Override
    public LocalRule getLocalRule(int index) {
        if (index < 0) {
            return leftPattern[Math.floorMod(Math.abs(index), leftPattern.length)];
        } else if (index >= localRules.length) {
            return rightPattern[(index - localRules.length) % rightPattern.length];
        } else {
            return localRules[index];
        }
    }
}
