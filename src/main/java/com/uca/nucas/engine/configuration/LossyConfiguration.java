/*
 * Developed by Matthias Percelay. Created on 05/03/19 02:38.
 * Last modified 05/03/19 02:38
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

public class LossyConfiguration implements Configuration {
    int[] contents;
    int startIndex;
    int usefulSize;
    int lostState;
    int lossRadius;

    public LossyConfiguration(int[] contents, int lostState, int lossRadius) {
        this.contents = contents;
        this.lostState = lostState;
        this.lossRadius = lossRadius;
        this.usefulSize = contents.length;
        this.startIndex = 0;
    }

    public LossyConfiguration(LossyConfiguration oldConf, int[] contents) {
        this.contents = contents;
        this.lostState = oldConf.lostState;
        this.lossRadius = oldConf.lossRadius;
        this.startIndex = oldConf.startIndex;
        this.usefulSize = oldConf.usefulSize;
    }

    @Override
    public int getCell(int index) {
        return contents[index];
    }

    @Override
    public void setCell(int index, int state) {
        contents[index] = state;
    }

    @Override
    public int getSize() {
        return usefulSize;
    }

    @Override
    public int getInitialSize() {
        return contents.length;
    }

    @Override
    public int getStartPoint() {
        return startIndex;
    }
}
