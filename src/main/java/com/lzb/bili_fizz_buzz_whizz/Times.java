package com.lzb.bili_fizz_buzz_whizz;

import lombok.ToString;

/**
 * <br/>
 * Created on : 2023-08-17 15:30
 * @author mac
 */
@ToString
public class Times implements Rule {

    private final int number;
    private final String word;

    Times(int number, String word) {
        this.number = number;
        this.word = word;
    }

    @Override
    public boolean apply(int x, RuleResult ruleResult) {
        return ruleResult.append(x % number == 0, word);
    }
}
