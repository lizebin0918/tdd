package com.lzb.bili_fizz_buzz_whizz;

import java.util.stream.Stream;

import lombok.ToString;

/**
 * <br/>
 * Created on : 2023-08-17 21:13
 * @author mac
 */
@ToString
public class AnyOf implements Rule {

    private final Rule[] rules;

    public AnyOf(Rule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean apply(int input, RuleResult ruleResult) {
        return Stream.of(rules).anyMatch(rule -> rule.apply(input, ruleResult));
    }
}
