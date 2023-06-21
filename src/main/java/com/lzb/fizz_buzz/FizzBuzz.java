package com.lzb.fizz_buzz;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Write a program that prints the numbers from 1 to 100. But for multiples of three print "Fizz" instead of the number and for the multiples of five print "Buzz". For numbers which are multiples of both three and five print "FizzBuzz".
 *
 * Sample output:
 *
 * 1
 * 2
 * Fizz
 * 4
 * Buzz
 * Fizz
 * 7
 * 8
 * Fizz
 * Buzz
 * 11
 * Fizz
 * 13
 * 14
 * FizzBuzz
 * 16
 * 17
 * Fizz
 * 19
 * Buzz<br/>
 * Created on : 2023-06-21 16:25
 * @author lizebin
 */
public class FizzBuzz {

    public static final String FIZZ = "Fizz";
    public static final String BUZZ = "Buzz";
    public static final String FIZZ_BUZZ = FIZZ + BUZZ;
    public static final int THREE = 3;
    public static final int FIVE = 5;
    public static final int FIFTEEN = 15;

    private final int number;
    public FizzBuzz(int number) {
        this.number = number;
    }

    List<String> print() {
        List<String> lines = new ArrayList<>(number);
        for (int i = 1; i <= number; i++) {
            /*
            // continue有点丑，转成 return Optional
            if (isMultiplesOf(i, FIFTEEN)) {
                lines.add(FIZZ_BUZZ);
                continue;
            }
            if (isMultiplesOf(i, THREE)) {
                lines.add(FIZZ);
                continue;
            }
            if (isMultiplesOf(i, FIVE)) {
                lines.add(BUZZ);
                continue;
            }*/
            int inputNumber = i;
            matchMultiplesOf(inputNumber).ifPresentOrElse(lines::add, () -> lines.add(Objects.toString(inputNumber)));
        }
        return lines;
    }

    public boolean isMultiplesOf(int inputNumber, int multiples) {
        return inputNumber % multiples == 0;
    }

    public Optional<String> matchMultiplesOf(int inputNumber) {
        if (isMultiplesOf(inputNumber, FIFTEEN)) {
            return Optional.of(FIZZ_BUZZ);
        }
        if (isMultiplesOf(inputNumber, THREE)) {
            return Optional.of(FIZZ);
        }
        if (isMultiplesOf(inputNumber, FIVE)) {
            return Optional.of(BUZZ);
        }
        return Optional.empty();
    }

}
