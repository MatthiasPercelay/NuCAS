/*
 * Developed by Matthias Percelay. Created on 02/03/19 13:52.
 * Last modified 02/03/19 13:52
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine;

/**
 * concrete implementation of RuleSet that keeps all the rules in an array
 * intended for use when rules are simply numbered
 */
public class SimpleRuleSet implements RuleSet {
    LocalRule[] rules;

    public int callRule(int ruleId, int position, Configuration conf) {
        return rules[ruleId].evaluate(position, conf);
    }

    public SimpleRuleSet(LocalRule[] rules) {
        this.rules = rules;
    }
}
