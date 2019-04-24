package com.uca.nucas.engine.ruleset.localrule.perturbationexample;

import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;

public class LeftGenRule implements LocalRule {
    @Override
    public int evaluate(int position, Configuration conf) {
        return 2;
    }

    @Override
    public int getRadius() {
        return 0;
    }

    @Override
    public int hashCode() {
        return "LeftGenRule".hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LeftGenRule);
    }
}
