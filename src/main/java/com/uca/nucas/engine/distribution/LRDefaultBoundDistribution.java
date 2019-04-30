/*
 * Developed by Matthias Percelay. Created on 06/03/19 16:18.
 * Last modified 06/03/19 16:18
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Distribution bound by a different default rule at each end
 * => period 1 pnu-ca
 */
public class LRDefaultBoundDistribution extends AbstractDistribution{
    LocalRule leftDefault;
    LocalRule rightDefault;

    public LRDefaultBoundDistribution(LocalRule[] rules, LocalRule leftDefault, LocalRule rightDefault) {
        super(rules);
        this.leftDefault = leftDefault;
        this.rightDefault = rightDefault;
    }

    @Override
    public LocalRule getLocalRule(int index) {
        if (index < 0) return leftDefault;
        else if (index >= localRules.length) return rightDefault;
        else return localRules[index];
    }

    @Override
    public HashSet<LocalRule> getSetOfRules() {
        var res = new HashSet<LocalRule>();
        res.addAll(Arrays.asList(localRules));
        res.add(leftDefault);
        res.add(rightDefault);
        return res;
    }
}
