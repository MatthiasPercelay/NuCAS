package com.uca.nucas.engine.ruleset.localrule.arbitraryrule;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;

public class WordNode extends WordTree {
    ArrayList<WordTree> children;

    public WordNode(int symbol, int defo, ArrayList<WordTree> children) {
        super(symbol, defo);
        this.children = children;
    }

    @Override
    int walk(Deque<Integer> word) {
        int  wildcardFound = -1;
        int input = word.removeFirst();
        if (input == -1) return -1;
        for (int i = 0; i < children.size(); i++) {
            WordTree c = children.get(i);
            if (c.symbol == WILDCARD) wildcardFound = i;
            if (c.symbol == input) return c.walk(word);
        }
        if (wildcardFound != -1) return children.get(wildcardFound).walk(word);
        return defo;
    }

    WordTree child(int symbol) {
        for (WordTree c : children) {
            if (c.symbol == symbol) {
                return c;
            }
        }

        return child(new WordNode(symbol, defo, new ArrayList<>()));
    }

    WordTree child (WordTree child) {
        if (!children.contains(child)) children.add(child);
        return child;
    }

    @Override
    WordTree leaf(int symbol, int value) {
        for (WordTree c : children) {
            if (c instanceof WordLeaf && c.symbol == symbol) {
                return c;
            }
        }

        return child(new WordLeaf(symbol, value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordNode wordNode = (WordNode) o;
        return symbol == wordNode.symbol && defo == wordNode.defo && Objects.equals(children, wordNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }
}
