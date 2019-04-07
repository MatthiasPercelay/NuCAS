/*
 * Developed by Matthias Percelay. Created on 13/03/19 17:19.
 * Last modified 13/03/19 17:19
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.utils;

import com.uca.nucas.engine.alphabet.Alphabet;
import com.uca.nucas.engine.Automaton;
import com.uca.nucas.engine.alphabet.BinaryAlphabet;
import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.configuration.GrowingConfiguration;
import com.uca.nucas.engine.configuration.LossyConfiguration;
import com.uca.nucas.engine.configuration.WrappingConfiguration;
import com.uca.nucas.engine.distribution.UniformDistribution;
import com.uca.nucas.engine.ruleset.SimpleRuleSet;
import com.uca.nucas.engine.ruleset.localrule.ElementaryRule;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Simple testing device for elementary automata
 */
public class ElementaryExample {
    public static void main(String[] args) {
        System.out.println("This example program simulates an elementary cellular automaton with a randomly generated");
        System.out.println("configuration, then saves the results to a png image");
        System.out.println("Input type of configuration: growing, lossy or wrapping");
        Scanner in = new Scanner(System.in);
        int confType = 0;
        while (confType == 0) {
            String type = in.next();
            if (type.equalsIgnoreCase("growing")) confType = 1;
            else if (type.equalsIgnoreCase("lossy")) confType = 2;
            else if (type.equalsIgnoreCase("wrapping")) confType = 3;
            else {
                System.out.println("Please input a valid configuration type");
            }
        }
        System.out.println("Input size of configuration");
        int size = in.nextInt();
        while (size < 1) {
            System.out.println("Please input a valid size");
            size = in.nextInt();
        }
        System.out.println("Input number of steps for the simulation to run");
        int steps = in.nextInt();
        while (steps < 1 || steps > size) {
            System.out.println("Please input a valid number of steps");
            steps = in.nextInt();
        }
        System.out.println("Input Wolfram code for the automaton");
        int code = in.nextInt();
        while (code < 0 || code > 255) {
            System.out.println("Please input a valid Wolfram code");
            code = in.nextInt();
        }
        in.close();

        int[] contents = new int[size];
        Random rand = new Random();
        for (int i = 0; i < contents.length; i++) {
            contents[i] = rand.nextInt(2);
        }

        Configuration conf;
        if (confType == 1) conf = new GrowingConfiguration(contents, 0);
        else if (confType == 2) conf = new LossyConfiguration(contents, -1);
        else conf = new WrappingConfiguration(contents);

        ElementaryRule rule = new ElementaryRule(code);
        UniformDistribution dist = new UniformDistribution(rule);

        Alphabet alphabet = new BinaryAlphabet();

        Automaton automaton = new Automaton(alphabet, dist, 1);

        BufferedImage img = new BufferedImage(size, steps + 1, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < size; i++) {
            img.setRGB(i, 0, FXToAWT(alphabet.getColor(conf.getCell(i))).getRGB());
        }

        for (int y = 1; y < steps; y++) {
            conf = automaton.evaluate(conf);
            for (int x = 0; x < size; x++) {
                img.setRGB(x, y, FXToAWT(alphabet.getColor(conf.getCell(x))).getRGB());
            }
        }

        try {
            RenderedImage rimg = img;
            ImageIO.write(rimg, "png", new File("exampleimage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static java.awt.Color FXToAWT(Color color) {
        return new java.awt.Color(
                (float)color.getRed(),
                (float)color.getGreen(),
                (float)color.getBlue(),
                (float)color.getOpacity());
    }
}
