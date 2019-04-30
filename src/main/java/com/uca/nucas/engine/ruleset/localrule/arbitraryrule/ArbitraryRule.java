package com.uca.nucas.engine.ruleset.localrule.arbitraryrule;

import com.uca.nucas.engine.configuration.Configuration;
import com.uca.nucas.engine.ruleset.localrule.LocalRule;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class ArbitraryRule implements LocalRule {
    private int[] neighbors;
    private int radius = 0;
    private WordTree tree;
    private int hash;

    public ArbitraryRule(int[] neighbors, List<WPattern> patterns, int defo) {
        this.neighbors = neighbors;
        for (int i : neighbors) {
            radius = Math.max(radius, Math.abs(i));
        }
        this.tree = WordTree.buildTree(patterns, defo);
        this.hash = patterns.hashCode();
    }

    @Override
    public int evaluate(int position, Configuration conf) {
        ArrayDeque<Integer> word = new ArrayDeque<>();
        for (int k : neighbors) {
            word.add(conf.getRelativeCell(position, k));
        }
        return tree.walk(word);
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArbitraryRule that = (ArbitraryRule) o;
        return getRadius() == that.getRadius() &&
                hash == that.hash &&
                Arrays.equals(neighbors, that.neighbors);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
