/*
 * Developed by Matthias Percelay. Created on 04/03/19 18:39.
 * Last modified 04/03/19 18:39
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

public class GrowingConfiguration implements Configuration {
    private int[] contents;
    private int originOffset;
    private int originalSize;

    public GrowingConfiguration(int[] contents, int originOffset, int originalSize) {
        this.contents = contents;
        this.originOffset = originOffset;
        this.originalSize = originalSize;
    }

    public GrowingConfiguration(int size, )

    @Override
    public int getCell(int index) {
        return 0;
    }

    @Override
    public void setCell(int index, int state) {
        contents[index] = state;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
