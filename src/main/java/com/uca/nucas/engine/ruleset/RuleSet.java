/*
 * Developed by Matthias Percelay. Created on 11/03/19 07:49.
 * Last modified 04/03/19 21:03
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.ruleset;

import com.uca.nucas.engine.configuration.Configuration;

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