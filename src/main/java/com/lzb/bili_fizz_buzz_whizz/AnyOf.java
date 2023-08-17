package com.lzb.bili_fizz_buzz_whizz;

import java.util.stream.Stream;

/**
 * <br/>
 * Created on : 2023-08-17 21:13
 * @author mac
 */
public class AnyOf {

    private final Times[] rules;

    public AnyOf(Times... rules) {
        this.rules = rules;
    }

    boolean apply(int input, RuleResult ruleResult) {
        return Stream.of(rules).anyMatch(rule -> rule.apply(input, ruleResult));
    }
}
