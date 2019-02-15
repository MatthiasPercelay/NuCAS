/*
 * Developed by Matthias Percelay. Created on 15/02/19 14:54.
 * Last modified 15/02/19 14:54
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas;

public class Rule110 implements CA1DRunnable{
    private int state;

    Rule110(int state, int localRuleID) {
        this.state = state;
    }

    public CA1DRunnable createInstance(int state, int localRuleID, EvaluationContext context) {
        return new Rule110(state, localRuleID);
    }

    public int getState() {
        return state;
    }

    public void setState(int newState) {
        state = newState;
    }

    public int getLocalRule() {
        return 0;
    }

    public void setLocalRule(int newRuleID) {
    }

    public boolean isInert() {
        return false;
    }

    public int runStep(EvaluationContext context) {
        if (state == 0) {
            if (context.getNeighborState(-1) == 0) {
                if (context.getNeighborState(1) == 0) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (context.getNeighborState(1) == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        } else {
            if (context.getNeighborState(-1) == 0) {
                if (context.getNeighborState(1) == 0) {
                    return 1;
                } else {
                    return 1;
                }
            } else {
                if (context.getNeighborState(1) == 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
