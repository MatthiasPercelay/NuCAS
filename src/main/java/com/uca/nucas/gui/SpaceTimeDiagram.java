package com.uca.nucas.gui;

import com.uca.nucas.engine.configuration.Configuration;
import java.util.ArrayList;

public class SpaceTimeDiagram {
    ArrayList<Configuration> data;
    int maxConfSize;

    public SpaceTimeDiagram() {
        data = new ArrayList<>();
        maxConfSize = 0;
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

    public int getConfCount() {
        return data.size();
    }

    public void append(Configuration conf) {
        data.add(conf);
        maxConfSize = Math.max(maxConfSize, conf.getSize());
    }

    public void clearComputations() {
        Configuration start = data.get(0);
        data = new ArrayList<>();
        data.add(start);
        maxConfSize = start.getGreatestSize();
    }

    public void setStartingConfiguration(Configuration startingConfiguration) {
        if (data.size() == 0) {
            data.add(startingConfiguration);
        } else {
            data.set(0, startingConfiguration);
        }
    }

    public void editStartingConfiguration(int index, int state) {
        if(data.size() != 0) {
            data.get(0).setCell(index, state);
        }
    }

    public int[] getSegment(int startPoint, int endPoint, int step) {
        Configuration configuration = data.get(step);
        startPoint = Math.max(0, startPoint);
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
