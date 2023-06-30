package com.lzb.fizz_buzz;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-06-28 22:55
 * @author mac
 */
class FizzBuzz1UnitTest {

    @Test
    void should_return_raw_number_when_input_raw_number() {
        FizzBuzz1 fizzBuzz1 = new FizzBuzz1(List.of(1));
        Assertions.assertEquals(fizzBuzz1.print(), List.of("1"));
    }

    @Test
    void should_return_fizz_when_input_is_three_multiples() {
        FizzBuzz1 fizzBuzz1 = new FizzBuzz1(List.of(3));
        Assertions.assertEquals(fizzBuzz1.print(), List.of("Fizz"));
    }

    @Test
    void should_return_Buzz_when_input_is_five_multiples() {
        FizzBuzz1 fizzBuzz1 = new FizzBuzz1(List.of(5));
        Assertions.assertEquals(fizzBuzz1.print(), List.of("Buzz"));
    }

    @Test
    void should_return_FizzBuzz_when_input_is_fifteen_multiples() {
        FizzBuzz1 fizzBuzz1 = new FizzBuzz1(List.of(15));
        Assertions.assertEquals(fizzBuzz1.print(), List.of("FizzBuzz"));
    }

}
