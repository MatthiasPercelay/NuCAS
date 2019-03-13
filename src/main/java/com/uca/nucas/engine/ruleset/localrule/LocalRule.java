/*
 * Developed by Matthias Percelay. Created on 11/03/19 07:51.
 * Last modified 04/03/19 21:03
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.ruleset.localrule;

import com.uca.nucas.engine.configuration.Configuration;

/**
 * Interface for local rules
 * may be declared a functional interface later
 */
public interface LocalRule {
    /**
     * take a configuration and the position of the cell within it, returns that cell's next state according to the
     * transitions defined in the rule
     * @param position
     * @param conf
     * @return
     */
    public int evaluate(int position, Configuration conf);
}
