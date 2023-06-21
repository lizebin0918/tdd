package com.lzb.fizz_buzz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FizzBuzzTests {

    @Test
    void should_multiples_of_three_when_number_is_6() {
        int number = 6;
        FizzBuzz fizzBuzz = new FizzBuzz(number);
        List<String> except = new ArrayList<>();
        except.add("1");
        except.add("2");
        except.add(FizzBuzz.FIZZ);
        except.add("4");
        except.add(FizzBuzz.BUZZ);
        except.add(FizzBuzz.FIZZ);
        Assertions.assertEquals(except, fizzBuzz.print());

    }

    @Test
    void should_multiples_of_three_when_number_is_20() {
        int number = 20;
        FizzBuzz fizzBuzz = new FizzBuzz(number);
        List<String> exceptList = new ArrayList<>();
        exceptList.add("1");
        exceptList.add("2");
        exceptList.add("Fizz");
        exceptList.add("4");
        exceptList.add("Buzz");
        exceptList.add("Fizz");
        exceptList.add("7");
        exceptList.add("8");
        exceptList.add("Fizz");
        exceptList.add("Buzz");
        exceptList.add("11");
        exceptList.add("Fizz");
        exceptList.add("13");
        exceptList.add("14");
        exceptList.add("FizzBuzz");
        exceptList.add("16");
        exceptList.add("17");
        exceptList.add("Fizz");
        exceptList.add("19");
        exceptList.add("Buzz");
        Assertions.assertEquals(exceptList, fizzBuzz.print());

    }

}