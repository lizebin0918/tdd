package com.lzb.bili_fizz_buzz_whizz;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        Rule rule = Rules.times(3, "Fizz");
        Optional<String> result = rule.apply(6);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Fizz", result.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 3, 103})
    @DisplayName("contains 3, return Fizz")
    void should_return_fizz_when_3_contains(int input) {
        Rule rule = Rules.contains(3, "Fizz");
        Optional<String> result = rule.apply(input);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Fizz", result.get());
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 13})
    @DisplayName("默认规则，什么都不匹配，直接输出")
    void should_return_default(int input) {
        Rule rule = Rules.defaultRule();
        Optional<String> result = rule.apply(input);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Objects.toString(input), result.get());
    }



    @Test
    @DisplayName("测试组合规则，3的倍数，返回Fizz，5的倍数，返回Buzz")
    void should_return_fizz_or_buzz() {
        Rule rule = Rules.anyOf(Rules.times(3, "Fizz"), Rules.times(5, "Buzz"));

        Optional<String> result3Times = rule.apply(9);
        Assertions.assertTrue(result3Times.isPresent());
        Assertions.assertEquals("Fizz", result3Times.get());

        Optional<String> result5Times = rule.apply(25);
        Assertions.assertTrue(result5Times.isPresent());
        Assertions.assertEquals("Buzz", result5Times.get());
    }

    @Test
    @DisplayName("测试组合规则，3个数的倍数")
    void should_return_fizzbuzzwhizz() {
        // given
        Rule rule = Rules.allOf(
                Rules.times(3, "Fizz"),
                Rules.times(5, "Buzz"),
                Rules.times(7, "Whizz")
        );

        Optional<String> result = rule.apply(3 * 5 * 7);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("FizzBuzzWhizz", result.get());
    }

    @Test
    @DisplayName("测试组合规则，2个数的倍数")
    void should_return_fizzbuzz() {
        Rule rule = Rules.allOf(
                Rules.times(3, "Fizz"),
                Rules.times(5, "Buzz")
        );
        Optional<String> result = rule.apply(3 * 5);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("FizzBuzz", result.get());
    }

    @Test
    @DisplayName("测试组合规则，3的倍数")
    void should_return_fizz() {
        Rule rule = Rules.allOf(
                Rules.times(3, "Fizz"),
                Rules.times(5, "Buzz")
        );
        Optional<String> result = rule.apply(12);
        Assertions.assertFalse(result.isPresent());
    }

    @TestFactory
    @DisplayName("fizz buzz whizz final test")
    Collection<DynamicTest> should_final_test() {
        Rule r1_3 = Rules.times(3, "Fizz");
        Rule r1_5 = Rules.times(5, "Buzz");
        Rule r1_7 = Rules.times(7, "Whizz");

        // 任意倍数
        Rule r1 = Rules.anyOf(r1_3, r1_5, r1_7);

        // 乘积倍数
        Rule r2 = Rules.anyOf(Rules.allOf(r1_3, r1_5, r1_7), Rules.allOf(r1_3, r1_5), Rules.allOf(r1_3, r1_7), Rules.allOf(r1_5, r1_7));

        // 包含3
        Rule r3 = Rules.contains(3, "Fizz");

        // 默认规则
        Rule rd = Rules.defaultRule();

        Rule rule = Rules.anyOf(r3, r2, r1, rd);

        return List.of(
                DynamicTest.dynamicTest("3 return Fizz", () -> {
                    Optional<String> result = rule.apply(3);
                    Assertions.assertTrue(result.isPresent());
                    Assertions.assertEquals("Fizz", result.get());
                }),

                DynamicTest.dynamicTest("5 return Buzz", () -> {
                    Optional<String> result = rule.apply(5);
                    Assertions.assertTrue(result.isPresent());
                    Assertions.assertEquals("Buzz", result.get());
                }),

                DynamicTest.dynamicTest("7 return Whizz", () -> {
                    Optional<String> result = rule.apply(7);
                    Assertions.assertTrue(result.isPresent());
                    Assertions.assertEquals("Whizz", result.get());
                }),

                DynamicTest.dynamicTest("3*5*7 return FizzBuzzWhizz", () -> {
                    Optional<String> result = rule.apply(3 * 5 * 7);
                    Assertions.assertTrue(result.isPresent());
                    Assertions.assertEquals("FizzBuzzWhizz", result.get());
                }),

                DynamicTest.dynamicTest("3*5 return FizzBuzz", () -> {
                    Optional<String> result = rule.apply(3 * 5);
                    Assertions.assertTrue(result.isPresent());
                    Assertions.assertEquals("FizzBuzz", result.get());
                }),

                DynamicTest.dynamicTest("4 return 4", () -> {
                    Optional<String> result = rule.apply(4);
                    Assertions.assertTrue(result.isPresent());
                    Assertions.assertEquals("4", result.get());
                })
        );
    }

    @ParameterizedTest
    @CsvSource({
            "3,Fizz",
            "5,Buzz",
            "7,Whizz",
            "105,FizzBuzzWhizz",
            "35,Fizz",
            "4,4",
            "21,FizzWhizz"
    })
    @DisplayName("fizz buzz whizz final test 1")
    void should_final_test_1(int input, String result) {
        Rule r1_3 = Rules.times(3, "Fizz");
        Rule r1_5 = Rules.times(5, "Buzz");
        Rule r1_7 = Rules.times(7, "Whizz");

        // 任意倍数
        Rule r1 = Rules.anyOf(r1_3, r1_5, r1_7);

        // 乘积倍数
        Rule r2 = Rules.anyOf(Rules.allOf(r1_3, r1_5, r1_7), Rules.allOf(r1_3, r1_5), Rules.allOf(r1_3, r1_7), Rules.allOf(r1_5, r1_7));

        // 包含3
        Rule r3 = Rules.contains(3, "Fizz");

        // 默认规则
        Rule rd = Rules.defaultRule();

        Rule rule = Rules.anyOf(r3, r2, r1, rd);

        Optional<String> resultOpt = rule.apply(input);
        Assertions.assertTrue(resultOpt.isPresent());
        Assertions.assertEquals(result, resultOpt.get());

    }

}
