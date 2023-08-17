package com.lzb.fizz_buzz_whizz;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * https://www.bilibili.com/video/BV1xs411X7iv/?spm_id_from=333.788.recommend_more_video.11&vd_source=a8ffd7af1e5eb1765c32f637267c1ef0
 * 题目描述<br/>
 * rule1
 * - times(3) -> Fizz
 * - times(5) -> Buzz
 * - times(7) -> Whizz
 * rule2
 * - times(3) &amp;&amp; times(5) &amp;&amp; times(7) -> FizzBuzzWhizz
 * - times(3) &amp;&amp; times(5) -> FizzBuzz
 * - times(3) &amp;&amp; times(7) -> FizzWhizz
 * - times(5) &amp;&amp; times(7) -> BuzzWhizz
 * rule3
 * - contains(3) -> Fizz
 * - the priority of contains(3) is highest
 * rd
 * - others -> others
 * Created on : 2023-08-10 11:38
 * @author mac
 */
class FizzBuzzWhizzUnitTest {

    @Test
    void should_print_when_num_is_not_one() {
        int fizzNum = 2;
        int buzzNum = 3;
        int whizzNum = 5;
        assertEquals("fizzwhizz", FizzBuzzWhizz.print(fizzNum * whizzNum, List.of(new Rule(fizzNum, FizzBuzzWhizz.FIZZ)), List.of(new Rule(fizzNum, FizzBuzzWhizz.FIZZ), new Rule(buzzNum, FizzBuzzWhizz.BUZZ), new Rule(whizzNum, FizzBuzzWhizz.WHIZZ))));
        assertEquals("fizzbuzzwhizz", FizzBuzzWhizz.print(fizzNum * buzzNum * whizzNum, List.of(new Rule(fizzNum, FizzBuzzWhizz.FIZZ)), List.of(new Rule(fizzNum, FizzBuzzWhizz.FIZZ), new Rule(buzzNum, FizzBuzzWhizz.BUZZ), new Rule(whizzNum, FizzBuzzWhizz.WHIZZ))));
    }

    @Test
    void should_print_when_num_is_one() {
        int number = 1;
        assertEquals("fizzbuzzwhizz", FizzBuzzWhizz.print(200, List.of(new Rule(number, FizzBuzzWhizz.FIZZ)), List.of(new Rule(number, FizzBuzzWhizz.FIZZ), new Rule(number, FizzBuzzWhizz.BUZZ), new Rule(number, FizzBuzzWhizz.WHIZZ))));
        assertEquals("fizzbuzzwhizz", FizzBuzzWhizz.print(23, List.of(new Rule(number, FizzBuzzWhizz.FIZZ)), List.of(new Rule(number, FizzBuzzWhizz.FIZZ), new Rule(number, FizzBuzzWhizz.BUZZ), new Rule(number, FizzBuzzWhizz.WHIZZ))));
        assertEquals("fizz", FizzBuzzWhizz.print(number, List.of(new Rule(number, FizzBuzzWhizz.FIZZ)), List.of(new Rule(number, FizzBuzzWhizz.FIZZ), new Rule(number, FizzBuzzWhizz.BUZZ), new Rule(number, FizzBuzzWhizz.WHIZZ))));

    }

    @Test
    void should_is_Whizz() {
        int whizzNum = 3;
        int input1 = RandomUtils.nextInt(1, 100) * whizzNum;
        assertTrue(FizzBuzzWhizz.isTimes(input1, new Rule(whizzNum, FizzBuzzWhizz.WHIZZ)).isPresent());
        int input = RandomUtils.nextInt(1, 100) * whizzNum + 1;
        assertFalse(FizzBuzzWhizz.isTimes(input, new Rule(whizzNum, FizzBuzzWhizz.WHIZZ)).isPresent());
    }

    @Test
    void should_is_Fizz() {
        int input1 = RandomUtils.nextInt(1, 100) * 2;
        assertTrue(FizzBuzzWhizz.isTimes(input1, new Rule(2, FizzBuzzWhizz.FIZZ)).isPresent());
        int input = RandomUtils.nextInt(1, 100) * 2 + 1;
        assertFalse(FizzBuzzWhizz.isTimes(input, new Rule(2, FizzBuzzWhizz.FIZZ)).isPresent());
    }

    @Test
    void should_is_Buzz() {
        int buzzNum = 2;
        int input1 = RandomUtils.nextInt(1, 100) * buzzNum;
        assertTrue(FizzBuzzWhizz.isTimes(input1, new Rule(buzzNum, FizzBuzzWhizz.BUZZ)).isPresent());
        int input = RandomUtils.nextInt(1, 100) * buzzNum + 1;
        assertFalse(FizzBuzzWhizz.isTimes(input, new Rule(buzzNum, FizzBuzzWhizz.BUZZ)).isPresent());
    }

    @Test
    void should_contains() {
        Optional<String> twentyMatchTwo = FizzBuzzWhizz.isContains(21, new Rule(2, FizzBuzzWhizz.FIZZ));
        assertTrue(twentyMatchTwo.isPresent());
        assertEquals(FizzBuzzWhizz.FIZZ, twentyMatchTwo.get());

        Optional<String> eightMatchOne = FizzBuzzWhizz.isContains(8, new Rule(1, FizzBuzzWhizz.FIZZ));
        assertFalse(eightMatchOne.isPresent());
    }

}
