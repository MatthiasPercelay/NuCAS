/*
 * Developed by Matthias Percelay. Created on 06/03/19 16:21.
 * Last modified 06/03/19 16:21
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

/**
 * Distribution bound by a different periodic region at each end
 * => pnu-ca
 */
public class PeriodicBoundDistribution implements Distribution{
    int[] centerRules;
    int[] leftPattern;
    int[] rightPattern;
    int leftPeriod;
    int rightPeriod;

    public PeriodicBoundDistribution(int[] centerRules, int[] leftPattern, int[] rightPattern, int leftPeriod, int rightPeriod) {
        this.centerRules = centerRules;
        this.leftPattern = leftPattern;
        this.rightPattern = rightPattern;
        this.leftPeriod = leftPeriod;
        this.rightPeriod = rightPeriod;
    }

    @Override
    public int getLocalRule(int index) {
        if (index < 0) {
            return leftPattern[Math.floorMod(Math.abs(index), leftPeriod)];
        } else if (index >= centerRules.length) {
            return rightPattern[(index - centerRules.length) % rightPeriod];
        } else {
            return centerRules[index];
        }
    }
}
