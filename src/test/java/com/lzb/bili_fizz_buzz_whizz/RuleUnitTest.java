package com.lzb.bili_fizz_buzz_whizz;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * <br/>
 * Created on : 2023-08-17 15:15
 * @author mac
 */
class RuleUnitTest {

    @ParameterizedTest
    @ValueSource(ints = { 9, 3, 123 })
    @DisplayName("3的倍数，返回Fizz")
    void should_return_fizz_when_3_times() {
        Times rule = new Times(3, "Fizz");
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(6, ruleResult));
        Assertions.assertEquals("Fizz", ruleResult.getResult());
    }

    @ParameterizedTest
    @ValueSource(ints = { 13, 3, 103 })
    @DisplayName("contains 3, return Fizz")
    void should_return_fizz_when_3_contains(int input) {
        Contains rule = new Contains(3, "Fizz");
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(input, ruleResult));
        Assertions.assertEquals("Fizz", ruleResult.getResult());
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 13 })
    @DisplayName("默认规则，什么都不匹配，直接输出")
    void should_return_default(int input) {
        Default rule = new Default();
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(input, ruleResult));
        Assertions.assertEquals(Objects.toString(input), ruleResult.getResult());
    }



}
