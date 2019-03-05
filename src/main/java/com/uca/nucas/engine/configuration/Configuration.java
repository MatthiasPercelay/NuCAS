/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:27.
 * Last modified 28/02/19 16:27
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

public interface Configuration {

    public int getCell(int index);

    public void setCell(int index, int state);

    public int getSize();
}
