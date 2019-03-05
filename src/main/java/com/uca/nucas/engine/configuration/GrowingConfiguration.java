/*
 * Developed by Matthias Percelay. Created on 04/03/19 20:58.
 * Last modified 04/03/19 20:28
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.configuration;

import com.uca.nucas.engine.configuration.Configuration;

public class GrowingConfiguration implements Configuration {
    private int[] contents;
    private int defaultState;
    private int growthRadius;
    private int originOffset;
    private int originalSize;

    public GrowingConfiguration(int[] contents, int defaultState, int growthRadius) {
        this.contents = contents;
        this.defaultState = defaultState;
        this.growthRadius = growthRadius;
        this.originalSize = contents.length;
        this.originOffset = 0;
    }

    public GrowingConfiguration(GrowingConfiguration oldConf, int[] contents) {
        this.contents = contents;
        this.defaultState = oldConf.defaultState;
        this.growthRadius = oldConf.growthRadius;
        this.originOffset = oldConf.originOffset;
        this.originalSize = oldConf.originalSize;
    }

    @Override
    public int getCell(int index) {
        if (index < 0 || index >= contents.length) return defaultState;
        else return contents[index];
    }

    @Override
    public void setCell(int index, int state) {
        contents[index] = state;
    }

    @Override
    public int getSize() {
        return contents.length;
    }

    @Override
    public int getInitialSize() {
        return originalSize;
    }

    @Override
    public int getStartPoint() {
        return 0;
    }
}
