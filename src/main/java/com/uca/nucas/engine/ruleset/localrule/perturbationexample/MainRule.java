package com.uca.nucas.engine.ruleset.localrule.perturbationexample;

import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;

public class MainRule implements LocalRule {
    @Override
    public int evaluate(int position, Configuration conf) {
        int x = conf.getRelativeCell(position, -1);
        int y = conf.getRelativeCell(position, 0);
        int z = conf.getRelativeCell(position, 1);

        if (x == 1 && (y == 1 || (y != 2 && z != 2))) return 1;
        else if (z == 2 && (y == 2 || (x != 1 && y != 1))) return 2;
        else return 0;
    }

    @Override
    public int getRadius() {
        return 1;
    }

    @Override
    public int hashCode() {
        return "MainRule".hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MainRule);
    }
}
