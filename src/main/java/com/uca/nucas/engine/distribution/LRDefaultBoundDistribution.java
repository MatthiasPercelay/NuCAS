/*
 * Developed by Matthias Percelay. Created on 06/03/19 16:18.
 * Last modified 06/03/19 16:18
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

/**
 * Distribution bound by a different default rule at each end
 * => period 1 pnu-ca
 */
public class LRDefaultBoundDistribution implements Distribution{
    int[] ruleIDs;
    int leftDefault;
    int rightDefault;

    public LRDefaultBoundDistribution(int[] ruleIDs, int leftDefault, int rightDefault) {
        this.ruleIDs = ruleIDs;
        this.leftDefault = leftDefault;
        this.rightDefault = rightDefault;
    }

    @Override
    public int getLocalRule(int index) {
        if (index < 0) return leftDefault;
        else if (index >= ruleIDs.length) return rightDefault;
        else return ruleIDs[index];
    }
}
