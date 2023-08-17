package com.lzb.bili_fizz_buzz_whizz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-08-17 15:15
 * @author mac
 */
class RuleUnitTest {

    @Test
    @DisplayName("3的倍数，返回Fizz")
    void should_return_fizz_when_3_times() {
        Rule rule = new Rule(3, "Fizz");
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(6, ruleResult));
        Assertions.assertEquals("Fizz", ruleResult.getResult());
    }

}
