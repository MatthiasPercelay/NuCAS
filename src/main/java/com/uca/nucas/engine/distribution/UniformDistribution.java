/*
 * Developed by Matthias Percelay. Created on 06/03/19 16:29.
 * Last modified 06/03/19 16:29
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;

/**
 * Distribution that only supports a single rule
 * => classical CA
 */
public class UniformDistribution implements Distribution {
    LocalRule rule;

    public UniformDistribution(LocalRule rule) {
        this.rule = rule;
    }

    @Override
    public LocalRule getLocalRule(int index) {
        return rule;
    }

    @Override
    public int getRadius() {
        return rule.getRadius();
    }

    @Override
    public void setLocalRule(int index, LocalRule rule) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("May not modify uniform distribution. Choose appropriate distribution type");
    }
}
