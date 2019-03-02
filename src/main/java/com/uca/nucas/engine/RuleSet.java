/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:47.
 * Last modified 28/02/19 16:47
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

/**
 * container for local rules
 */
public interface RuleSet {

    /**
     * calls the local rule with ID ruleID on the cell at position in conf
     * and returns the state ID of the cell's next state
     * @param ruleId
     * @param position
     * @param conf
     * @return
     */
    public int callRule(int ruleId, int position, Configuration conf);
}