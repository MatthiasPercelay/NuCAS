package com.uca.nucas.engine.ruleset.localrule.arbitraryrule;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class WordTree {
    static final int WILDCARD = -42;
    static final int SENTINEL = -2;

    int symbol;
    int defo;

    WordTree(int symbol, int defo) {
        this.symbol = symbol;
        this.defo = defo;
    }

    abstract int walk(Deque<Integer> word);

    abstract WordTree child(int symbol);
    abstract WordTree child(WordTree child);
    abstract WordTree leaf(int symbol, int value);

    static WordTree buildTree(List<WPattern> patterns, int defo) {
        WordTree tree = new WordNode(SENTINEL, defo, new ArrayList<>());
        WordTree current = tree;

        for (WPattern p : patterns) {
            WordTree root = current;

            for (int i = 0; i < p.symbols.size() - 1; i++) {
                int sym = p.symbols.get(i);
                current = current.child(sym);
            }

            current = current.leaf(p.symbols.get(p.symbols.size() - 1), p.value);

            current = root;
        }
        return tree;
    }
}
