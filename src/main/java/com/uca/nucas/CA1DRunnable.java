/*
 * Developed by Matthias Percelay. Created on 15/02/19 13:40.
 * Last modified 15/02/19 13:39
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas;

/**
 * Interface defining all necessary internal operations
 * for simulation of 1D automata
 */
public interface CA1DRunnable {
    CA1DRunnable createInstance(int state, int localRuleID, EvaluationContext context);

    int getState();
    void setState(int newState);

    int getLocalRule();
    void setLocalRule(int newRuleID);

    boolean isInert();

    int runStep(EvaluationContext context);
}
