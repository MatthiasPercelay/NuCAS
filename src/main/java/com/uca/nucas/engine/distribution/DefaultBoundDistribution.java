/*
 * Developed by Matthias Percelay. Created on 05/03/19 12:05.
 * Last modified 05/03/19 12:05
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Distribution bound by the same default rule at both ends
 * => dnu-ca
 */
public class DefaultBoundDistribution extends AbstractDistribution {
    LocalRule defaultRule;

    public DefaultBoundDistribution(LocalRule[] rules, LocalRule defaultRule) {
        super(rules);
        this.defaultRule = defaultRule;
    }

    @Override
    public LocalRule getLocalRule(int index) {
        if (index < 0 || index >= localRules.length) return defaultRule;
        else return localRules[index];
    }

    @Override
    public HashSet<LocalRule> getSetOfRules() {
        HashSet<LocalRule> res = new HashSet<>();
        res.addAll(Arrays.asList(localRules));
        res.add(defaultRule);
        return res;
    }
}
