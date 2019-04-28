package com.uca.nucas.engine.ruleset.localrule.arbitraryrule;

import java.util.Deque;
import java.util.Objects;

public class WordLeaf extends WordTree {
    int value;

    public WordLeaf(int symbol, int value) {
        super(symbol, SENTINEL);
        this.value = value;
    }

    @Override
    int walk(Deque<Integer> word) {
        return value;
    }

    @Override
    WordTree child(int symbol) {
        return null;
    }

    @Override
    WordTree child(WordTree child) {
        return null;
    }

    @Override
    WordTree leaf(int symbol, int value) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordLeaf wordLeaf = (WordLeaf) o;
        return symbol == wordLeaf.symbol && value == wordLeaf.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
