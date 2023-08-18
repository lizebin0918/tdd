package com.lzb.bili_fizz_buzz_whizz;

import java.util.stream.Stream;

/**
 * <br/>
 * Created on : 2023-08-18 10:15
 * @author mac
 */
public final class Rules {

    public static Rule times(int number, String word) {
        return (x, rr) -> rr.append(x % number == 0, word);
    }

    public static Rule contains(int number, String word) {
        return (x, rr) -> rr.append(String.valueOf(x).contains(String.valueOf(number)), word);
    }

    public static Rule defaultRule() {
        return (x, rr) -> rr.append(true, String.valueOf(x));
    }

    public static Rule anyOf(Rule... rules) {
        return (x, rr) -> Stream.of(rules).anyMatch(rule -> rule.apply(x, rr));
    }

    public static Rule allOf(Rule... rules) {
        RuleResult temp = new RuleResult();
        return (x, rr) -> rr.append(Stream.of(rules).allMatch(rule -> rule.apply(x, temp)), temp.getResult());
    }

    private Rules() {}

}
