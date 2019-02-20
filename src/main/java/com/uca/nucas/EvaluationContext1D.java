/*
 * Developed by Matthias Percelay. Created on 15/02/19 15:19.
 * Last modified 15/02/19 15:19
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas;

/**
 * interface defining operations the context needs to provide to automata during
 * simulation
 */
public interface EvaluationContext1D {
    int getNeighborState(int offset);
}
