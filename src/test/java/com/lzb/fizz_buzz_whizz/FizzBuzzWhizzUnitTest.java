package com.lzb.fizz_buzz_whizz;

import java.util.Objects;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(fizzBuzzWhizz.isWhizzTimes(RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.whizzNum()));
        assertFalse(fizzBuzzWhizz.isWhizzTimes(RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.whizzNum() + 1));
    }

    @Test
    void should_is_Fizz() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(2, 2, 3);
        assertTrue(fizzBuzzWhizz.isFizzTimes(RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.fizzNum()));
        assertFalse(fizzBuzzWhizz.isFizzTimes(RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.fizzNum() + 1));
    }

    @Test
    void should_is_Buzz() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(1, 2, 3);
        assertTrue(fizzBuzzWhizz.isBuzzTimes(RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.buzzNum()));
        assertFalse(fizzBuzzWhizz.isBuzzTimes(RandomUtils.nextInt(1, 100) * fizzBuzzWhizz.buzzNum() + 1));
    }

    @Test
    void should_contains() {
        FizzBuzzWhizz fizzBuzzWhizz = new FizzBuzzWhizz(1, 2, 3);
        assertTrue(fizzBuzzWhizz.isContains(21));
        assertTrue(fizzBuzzWhizz.isContains(31));
        assertTrue(fizzBuzzWhizz.isContains(101));
        assertTrue(fizzBuzzWhizz.isContains(11));
        assertFalse(fizzBuzzWhizz.isContains(22));
    }

}
