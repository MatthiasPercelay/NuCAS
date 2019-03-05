/*
 * Developed by Matthias Percelay. Created on 04/03/19 20:59.
 * Last modified 04/03/19 20:59
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;


public class WrappingConfiguration implements Configuration {
    int[] contents;

    public WrappingConfiguration(int[] contents) {
        this.contents = contents;
    }

    public WrappingConfiguration(int size) {
        this.contents = new int[size];
    }

    @Override
    public int getCell(int index) {
        index %= contents.length;
        if (index < 0) index += contents.length;
        return contents[index];
    }

    @Override
    public void setCell(int index, int state) {
        index %= contents.length;
        if (index < 0) index += contents.length;
        contents[index] = state;
    }

    @Override
    public int getSize() {
        return contents.length;
    }

    @Override
    public int getInitialSize() {
        return getSize();
    }

    @Override
    public int getStartPoint() {
        return 0;
    }
}
