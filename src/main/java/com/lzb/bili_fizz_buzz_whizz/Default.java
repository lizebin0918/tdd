package com.lzb.bili_fizz_buzz_whizz;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-08-17 21:08
 * @author mac
 */
public class Default {
    boolean apply(int input, RuleResult ruleResult) {
        return ruleResult.append(true, Objects.toString(input));
    }
}
