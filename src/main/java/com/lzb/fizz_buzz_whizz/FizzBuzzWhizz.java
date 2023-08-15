package com.lzb.fizz_buzz_whizz;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-08-11 11:22
 * @author lizebin
 */
public record FizzBuzzWhizz(int fizzNum, int buzzNum, int whizzNum) {

    public static final String WHIZZ = "whizz";
    public static final String BUZZ = "buzz";
    public static final String FIZZ = "fizz";


    String print(int input) {
        if (isContains(input)) {
            return FIZZ;
        }
        StringBuilder sb = new StringBuilder();
        if (isFizzTimes(input)) {
            sb.append(FIZZ);
        }
        if (isBuzzTimes(input)) {
            sb.append(BUZZ);
        }
        if (isWhizzTimes(input)) {
            sb.append(WHIZZ);
        }
        if (sb.length() == 0) {
            sb.append(input);
        }
        return sb.toString();
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
