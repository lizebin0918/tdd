package com.lzb.bili_fizz_buzz_whizz;

/**
 * <br/>
 * Created on : 2023-08-17 18:19
 * @author mac
 */
public class Contains {

    private final int number;
    private final String word;

    public Contains(int number, String word) {
        this.number = number;
        this.word = word;
    }

    public boolean apply(int x, RuleResult ruleResult) {
        return ruleResult.append(String.valueOf(x).contains(String.valueOf(number)), word);
    }

}
