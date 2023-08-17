package com.lzb.bili_fizz_buzz_whizz;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * <br/>
 * Created on : 2023-08-17 15:15
 * @author mac
 */
class RuleUnitTest {

    @ParameterizedTest
    @ValueSource(ints = {9, 3, 123})
    @DisplayName("3的倍数，返回Fizz")
    void should_return_fizz_when_3_times() {
        Times rule = new Times(3, "Fizz");
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(6, ruleResult));
        Assertions.assertEquals("Fizz", ruleResult.getResult());
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 3, 103})
    @DisplayName("contains 3, return Fizz")
    void should_return_fizz_when_3_contains(int input) {
        Contains rule = new Contains(3, "Fizz");
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(input, ruleResult));
        Assertions.assertEquals("Fizz", ruleResult.getResult());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 13})
    @DisplayName("默认规则，什么都不匹配，直接输出")
    void should_return_default(int input) {
        Default rule = new Default();
        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(input, ruleResult));
        Assertions.assertEquals(Objects.toString(input), ruleResult.getResult());
    }

    @Test
    @DisplayName("测试组合规则，3的倍数，返回Fizz，5的倍数，返回Buzz")
    void should_return_fizz_or_buzz() {
        AnyOf rule = new AnyOf(new Times(3, "Fizz"), new Times(5, "Buzz"));

        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(9, ruleResult));
        Assertions.assertEquals("Fizz", ruleResult.getResult());

        RuleResult ruleResult1 = new RuleResult();
        Assertions.assertTrue(rule.apply(25, ruleResult1));
        Assertions.assertEquals("Buzz", ruleResult1.getResult());
    }

    @Test
    @DisplayName("测试组合规则，3个数的倍数")
    void should_return_fizzbuzzwhizz() {
        // given
        AllOf rule = new AllOf(
                new Times(3, "Fizz"),
                new Times(5, "Buzz"),
                new Times(7, "Whizz")
        );

        RuleResult ruleResult = new RuleResult();
        Assertions.assertTrue(rule.apply(3 * 5 * 7, ruleResult));
        Assertions.assertEquals("FizzBuzzWhizz", ruleResult.getResult());
    }

    @Test
    @DisplayName("测试组合规则，2个数的倍数")
    void should_return_fizzbuzz() {
        AllOf rule = new AllOf(
                new Times(3, "Fizz"),
                new Times(5, "Buzz")
        );
        RuleResult ruleResult1 = new RuleResult();
        Assertions.assertTrue(rule.apply(3 * 5, ruleResult1));
        Assertions.assertEquals("FizzBuzz", ruleResult1.getResult());
    }

}
