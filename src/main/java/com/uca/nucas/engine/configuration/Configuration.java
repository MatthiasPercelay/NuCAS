/*
 * Developed by Matthias Percelay. Created on 04/03/19 20:58.
 * Last modified 04/03/19 20:28
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.Automaton;

/**
 * Interface defining operations on configurations
 */
public interface Configuration {

    /**
     * returns the state of the cell at position index
     * @param index the integer offset of the cell within the configuration
     * @return the state of the cell at position index
     */
    public int getCell(int index);

    /**
     * returns the state of the cell at position + offset
     * @param position
     * @param offset
     * @return
     */
    public default int getRelativeCell(int position, int offset) {
        return getCell(position + offset);
    }

    /**
     * setter for the state of the cell at position index
     * @param index the position of the cell to set
     * @param state the state to set the cell to
     */
    public void setCell(int index, int state);

    /**
     * returns the size of the active part of the configuration
     * @return
     */
    public int getSize();

    /**
     * returns the original size of the configuration before growth or loss of information
     * @return
     */
    public int getInitialSize();

    /**
     * return the index to start iterating at for evaluation purposes
     * @return
     */
    public int getStartPoint();

    public int getDistributionOffset();

    public Configuration accept(Automaton automaton);
}
