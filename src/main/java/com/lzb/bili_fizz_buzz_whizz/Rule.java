package com.lzb.bili_fizz_buzz_whizz;

/**
 * <br/>
 * Created on : 2023-08-17 15:30
 * @author mac
 */
public class Rule {

    private final int number;
    private final String word;

    Rule(int number, String word) {
        this.number = number;
        this.word = word;
    }

    public boolean apply(int x, RuleResult ruleResult) {
        boolean match = x % number == 0;
        if (match) {
            ruleResult.append(word);
        }
        return match;
    }

}
