package com.lzb.bili_fizz_buzz_whizz;

import java.util.stream.Stream;

/**
 * <br/>
 * Created on : 2023-08-17 21:34
 * @author mac
 */
public class AllOf {

    private final Times[] times;

    public AllOf(Times... times) {
        this.times = times;
    }


    boolean apply(int input, RuleResult ruleResult) {
        return Stream.of(times).allMatch(rule -> rule.apply(input, ruleResult));
    }
}
