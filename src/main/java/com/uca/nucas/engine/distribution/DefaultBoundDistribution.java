/*
 * Developed by Matthias Percelay. Created on 05/03/19 12:05.
 * Last modified 05/03/19 12:05
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

public class DefaultBoundDistribution implements Distribution {
    int[] ruleIDs;
    int defaultRule;

    public DefaultBoundDistribution(int[] ruleIDs, int defaultRule) {
        this.ruleIDs = ruleIDs;
        this.defaultRule = defaultRule;
    }

    @Override
    public int getLocalRule(int index) {
        if (index < 0 || index >= ruleIDs.length) return defaultRule;
        else return ruleIDs[index];
    }
}
