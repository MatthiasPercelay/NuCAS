/*
 * Developed by Matthias Percelay. Created on 28/02/19 16:21.
 * Last modified 28/02/19 16:21
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.alphabet;


import javafx.scene.paint.Color;

/**
 * represents an alphabet over which automata are defined
 * complete WIP, expect breaking changes
 */
public class Alphabet {
    int size;
    Color[] colors;
    Color lostColor;

    public Alphabet(int size) {
        this.size = size;
        this.colors = new Color[size];
        for (int i = 0; i < this.size; i++) {
            this.colors[i] = Color.WHITE;
        }
        this.lostColor = Color.DARKGRAY;
    }

    public Color getColor(int state) {
        if (state == -1) return lostColor;
        return colors[state];
    }

    public void setColor(int state, Color color) {
        colors[state] = color;
    }

    public Color getLostColor() {
        return lostColor;
    }

    public void setLostColor(Color lostColor) {
        this.lostColor = lostColor;
    }
}
