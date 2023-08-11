package com.lzb.fizz_buzz_whizz;

import org.junit.jupiter.api.BeforeEach;
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

    private FizzBuzzWhizz fizzBuzzWhizz;

    @BeforeEach
    void setUp() {
        fizzBuzzWhizz = new FizzBuzzWhizz(1, 2, 3);
    }

    @Test
    void should_instance_FizzBuzzWhizz() {
        assertEquals(1, fizzBuzzWhizz.fizzNum());
        assertEquals(2, fizzBuzzWhizz.buzzNum());
        assertEquals(3, fizzBuzzWhizz.whizzNum());
    }

    @Test
    void should_print() {
        // given
        int i = 3;

        // when
        String r = fizzBuzzWhizz.print(i);

        // then
        assertEquals("fizzwhizz", r);
    }

    @Test
    void should_is_Whizz() {
        assertTrue(fizzBuzzWhizz.isWhizzTimes(3));
        assertFalse(fizzBuzzWhizz.isWhizzTimes(4));
        assertFalse(fizzBuzzWhizz.isWhizzTimes(5));
    }

    @Test
    void should_is_Fizz() {
        assertTrue(fizzBuzzWhizz.isFizzTimes(1));
        assertTrue(fizzBuzzWhizz.isFizzTimes(2));
        assertTrue(fizzBuzzWhizz.isFizzTimes(3));
    }

    @Test
    void should_is_Buzz() {
        assertFalse(fizzBuzzWhizz.isBuzzTimes(1));
        assertTrue(fizzBuzzWhizz.isBuzzTimes(2));
        assertFalse(fizzBuzzWhizz.isBuzzTimes(3));
        assertTrue(fizzBuzzWhizz.isBuzzTimes(4));
    }

    @Test
    void should_contains() {
        assertTrue(fizzBuzzWhizz.isContains(21));
        assertTrue(fizzBuzzWhizz.isContains(31));
        assertTrue(fizzBuzzWhizz.isContains(101));
        assertTrue(fizzBuzzWhizz.isContains(11));
        assertFalse(fizzBuzzWhizz.isContains(22));
    }

}
