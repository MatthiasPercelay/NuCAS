/*
 * Developed by Matthias Percelay. Created on 06/03/19 16:29.
 * Last modified 06/03/19 16:29
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

/**
 * Distribution that only supports a single rule
 * => classical CA
 */
public class UniformDistribution implements Distribution {
    int ruleID;

    public UniformDistribution(int ruleID) {
        this.ruleID = ruleID;
    }

    @Override
    public int getLocalRule(int index) {
        return ruleID;
    }
}
