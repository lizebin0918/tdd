package com.lzb.bili_fizz_buzz_whizz;

import java.util.stream.Stream;

import lombok.ToString;

/**
 * <br/>
 * Created on : 2023-08-17 21:34
 * @author mac
 */
@ToString
public class AllOf implements Rule {

    private final Rule[] rules;

    public AllOf(Rule... rules) {
        this.rules = rules;
    }


    @Override
    public boolean apply(int input, RuleResult ruleResult) {
        RuleResult temp = new RuleResult();
        return ruleResult.append(isAllMatch(input, temp), temp.getResult());
    }

    private boolean isAllMatch(int input, RuleResult ruleResult) {
        return Stream.of(rules).allMatch(rule -> {
            // 只要命中就会对ruleResult产生副作用
            return rule.apply(input, ruleResult);
        });
    }
}
