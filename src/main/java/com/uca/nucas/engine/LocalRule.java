/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:46.
 * Last modified 28/02/19 16:46
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

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
