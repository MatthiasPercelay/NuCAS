/*
 * Developed by Matthias Percelay. Created on 15/02/19 18:47.
 * Last modified 15/02/19 18:47
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SimpleRunner implements EvaluationContext1D {
    Rule110[] simulation = new Rule110[201];
    int index;

    public static void main(String[] args) {
        SimpleRunner runner = new SimpleRunner();
        runner.run();
    }

    public SimpleRunner() {
        Random rand = new Random();
        for (int i = 0; i < 201; i++) {
            simulation[i] = new Rule110(rand.nextInt(2), 0);
        }
        index = 0;
    }

    public int getNeighborState(int offset) {
        int i = (index + offset) % 201;
        if (i < 0) i += 201;
        return simulation[i].getState();
    }

    public void run() {
        BufferedImage img = new BufferedImage(201, 201, BufferedImage.TYPE_INT_BGR);
        for (int i = 0; i < 201; i++) {
            if (simulation[i].getState() == 0) img.setRGB(0, i, Color.WHITE.getRGB());
            else img.setRGB(0, i, Color.BLACK.getRGB());
        }

        for (int x = 1; x < 201; x++) {
            runStep();
            for (int y = 0; y < 201; y++) {
                if (simulation[y].getState() == 0) img.setRGB(x, y, Color.WHITE.getRGB());
                else img.setRGB(x, y, Color.BLACK.getRGB());
            }
        }

        try {
            RenderedImage rimg = img;
            ImageIO.write(rimg, "bmp", new File("testimage.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void runStep() {
        Rule110[] nextStep = new Rule110[201];
        while (index < 201) {
            nextStep[index] = new Rule110(simulation[index].runStep(this), 0);
            index++;
        }
        index = 0;
        simulation = nextStep;
    }
}
