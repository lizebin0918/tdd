package com.lzb.fizz_buzz_whizz;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-08-11 11:22
 * @author lizebin
 */
public record FizzBuzzWhizz(int fizzNum, int buzzNum, int whizzNum) {

    String print(int input) {
        return Objects.toString(input);
    }

    boolean isWhizzTimes(int input) {
        return input % whizzNum == 0;
    }

    boolean isFizzTimes(int input) {
        return input % fizzNum == 0;
    }

    boolean isBuzzTimes(int input) {
        return input % buzzNum == 0;
    }

    boolean isContains(int input) {
        return Objects.toString(input).contains(Objects.toString(fizzNum));
    }
}
