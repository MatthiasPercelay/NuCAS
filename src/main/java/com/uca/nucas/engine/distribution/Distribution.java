/*
 * Developed by Matthias Percelay. Created on 04/03/19 20:58.
 * Last modified 04/03/19 03:11
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.distribution;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;

import java.util.HashSet;

/**
 * Interface defining operations on distributions of rules
 */
public interface Distribution {

    /**
     * returns the id of the local rule at position index
     * @param index position in the distribution
     * @return id of local rule
     */
    public LocalRule getLocalRule(int index);

    public void setLocalRule(int index, LocalRule rule) throws UnsupportedOperationException;

    public int getRadius();

    public HashSet<LocalRule> getSetOfRules();
}
