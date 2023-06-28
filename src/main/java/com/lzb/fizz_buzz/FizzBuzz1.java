package com.lzb.fizz_buzz;

import java.util.List;
import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-06-28 22:59
 * @author mac
 */
public class FizzBuzz1 {

    private final List<Integer> inputList;

    public FizzBuzz1(List<Integer> inputList) {
        this.inputList = inputList;
    }

    public List<String> print() {
        return inputList.stream().map(number -> {
            if (number % 3 == 0 && number % 5 == 0) {
                return "FizzBuzz";
            }
            if (number % 3 == 0) {
                return "Fizz";
            }
            if (number % 5 == 0) {
                return "Buzz";
            }
            return Objects.toString(number);
        }).toList();
    }
}
