package com.uca.nucas.gui;

import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.ruleset.localrule.perturbationexample.MainRule;

import java.util.ArrayList;

public class SpaceTimeDiagram {
    ArrayList<Configuration> data;
    int maxConfSize;
    int maxOffset;

    public SpaceTimeDiagram() {
        data = new ArrayList<>();
        maxConfSize = 0;
        maxOffset = 0;
    }

    public Configuration get(int index) {
        return data.get(index);
    }

    public Configuration getLast() {
        return data.get(data.size() - 1);
    }

    public int getMaxConfSize() {
        return maxConfSize;
    }

    public int getMaxDistOffset() {
        return maxOffset;
    }

    public int getConfCount() {
        return data.size();
    }

    /**
     * appends a new configuration to the space time diagram
     * @param conf
     */
    public void append(Configuration conf) {
        data.add(conf);
        maxConfSize = Math.max(maxConfSize, conf.getSize());
        maxOffset = Math.max(maxOffset, conf.getDistributionOffset());
    }

    /**
     * resets the space time diagram to the starting configuration
     */
    public void clearComputations() {
        Configuration start = data.get(0);
        data = new ArrayList<>();
        data.add(start);
        maxConfSize = start.getGreatestSize();
        maxOffset = start.getDistributionOffset();
    }

    public void setStartingConfiguration(Configuration startingConfiguration) {
        if (data.size() == 0) {
            data.add(startingConfiguration);
        } else {
            data.set(0, startingConfiguration);
        }
    }

    /**
     * updates the starting configuration provided that the update is within bounds
     * @param index
     * @param state
     */
    public void editStartingConfiguration(int index, int state) {
        if(data.size() != 0 && index >= 0 && index <= data.get(0).getSize()) {
            data.get(0).setCell(index, state);
        }
    }

    /**
     * returns a segment of a configuration for display purposes
     * @param startPoint
     * @param endPoint
     * @param step
     * @return
     */
    public int[] getSegment(int startPoint, int endPoint, int step) {
        Configuration configuration = data.get(step);
        startPoint = Math.min(startPoint, maxConfSize);
        endPoint = Math.min(endPoint, maxConfSize);

        int size = endPoint - startPoint;

        int[] res = new int[size];

        for (int i = 0; i < res.length; i++) {
            res[i] = configuration.getCell(i + startPoint);
        }

        return res;
    }
}
