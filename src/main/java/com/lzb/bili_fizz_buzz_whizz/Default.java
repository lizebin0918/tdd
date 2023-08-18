package com.lzb.bili_fizz_buzz_whizz;

import java.util.Objects;

import lombok.ToString;

/**
 * <br/>
 * Created on : 2023-08-17 21:08
 * @author mac
 */
@ToString
public class Default implements Rule {

    @Override
    public boolean apply(int input, RuleResult ruleResult) {
        return ruleResult.append(true, Objects.toString(input));
    }
}
