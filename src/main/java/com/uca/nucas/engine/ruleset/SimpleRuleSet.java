/*
 * Developed by Matthias Percelay. Created on 11/03/19 07:49.
 * Last modified 04/03/19 21:03
 * Copyright (c) 2019. All rights reserved
 */

package com.uca.nucas.engine.ruleset;

import com.uca.nucas.engine.ruleset.localrule.LocalRule;
import com.uca.nucas.engine.configuration.Configuration;

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
