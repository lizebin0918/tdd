package com.lzb.fizz_buzz_whizz;

import java.util.Objects;
import java.util.Optional;

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

        Optional<String> contains = isContains(input, new Rule(fizzNum(), FIZZ));
        if (contains.isPresent()) {
            return contains.get();
        }

        StringBuilder sb = new StringBuilder();

        Optional<String> fizzTimes = isTimes(input, new Rule(fizzNum(), FIZZ));
        fizzTimes.ifPresent(sb::append);

        Optional<String> buzzTimes = isTimes(input, new Rule(buzzNum(), BUZZ));
        buzzTimes.ifPresent(sb::append);

        Optional<String> whizzTimes = isTimes(input, new Rule(whizzNum(), WHIZZ));
        whizzTimes.ifPresent(sb::append);

        if (sb.length() == 0) {
            sb.append(input);
        }

        return sb.toString();
    }

    static Optional<String> isContains(int input, Rule rule) {
        String inputString = Objects.toString(input);
        if (inputString.contains(Objects.toString(rule.number()))) {
            return Optional.of(rule.word());
        }
        return Optional.empty();
    }

    static Optional<String> isTimes(int input, Rule rule) {
        if (input % rule.number() == 0) {
            return Optional.of(rule.word());
        }
        return Optional.empty();
    }

}
