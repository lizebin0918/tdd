package com.lzb.fizz_buzz_whizz;

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
    void should_instance_FizzBuzzWhizz() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(2, 3, 4);
        assertEquals(fizzBuzzWhizz.fizzNum(), fizzBuzzWhizz.fizzNum());
        assertEquals(fizzBuzzWhizz.buzzNum(), fizzBuzzWhizz.buzzNum());
        assertEquals(fizzBuzzWhizz.whizzNum(), fizzBuzzWhizz.whizzNum());
    }

    @Test
    void should_print_when_num_is_not_one() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(2, 3, 5);
        assertEquals("fizzwhizz", fizzBuzzWhizz.print(fizzBuzzWhizz.fizzNum() * fizzBuzzWhizz.whizzNum()));
        assertEquals("fizzbuzzwhizz", fizzBuzzWhizz.print(fizzBuzzWhizz.fizzNum() * fizzBuzzWhizz.buzzNum() * fizzBuzzWhizz.whizzNum()));
    }

    @Test
    void should_print_when_num_is_one() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(1, 1, 1);
        assertEquals("fizzbuzzwhizz", fizzBuzzWhizz.print(200));
        assertEquals("fizzbuzzwhizz", fizzBuzzWhizz.print(23));
        assertEquals("fizz", fizzBuzzWhizz.print(1));

    }

    @Test
    void should_is_Whizz() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(1, 2, 3);
        int input1 = RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.whizzNum();
        assertTrue(FizzBuzzWhizz.isTimes(input1, new Rule(fizzBuzzWhizz.whizzNum(), FizzBuzzWhizz.WHIZZ)).isPresent());
        int input = RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.whizzNum() + 1;
        assertFalse(FizzBuzzWhizz.isTimes(input, new Rule(fizzBuzzWhizz.whizzNum(), FizzBuzzWhizz.WHIZZ)).isPresent());
    }

    @Test
    void should_is_Fizz() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(2, 2, 3);
        int input1 = RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.fizzNum();
        assertTrue(FizzBuzzWhizz.isTimes(input1, new Rule(fizzBuzzWhizz.fizzNum(), FizzBuzzWhizz.FIZZ)).isPresent());
        int input = RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.fizzNum() + 1;
        assertFalse(FizzBuzzWhizz.isTimes(input, new Rule(fizzBuzzWhizz.fizzNum(), FizzBuzzWhizz.FIZZ)).isPresent());
    }

    @Test
    void should_is_Buzz() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(1, 2, 3);
        int input1 = RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.buzzNum();
        assertTrue(FizzBuzzWhizz.isTimes(input1, new Rule(fizzBuzzWhizz.buzzNum(), FizzBuzzWhizz.BUZZ)).isPresent());
        int input = RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.buzzNum() + 1;
        assertFalse(FizzBuzzWhizz.isTimes(input, new Rule(fizzBuzzWhizz.buzzNum(), FizzBuzzWhizz.BUZZ)).isPresent());
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
