package com.lzb.fizz_buzz_whizz;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <br/>
 * Created on : 2023-08-11 11:22
 * @author lizebin
 */
public record FizzBuzzWhizz() {

    public static final String WHIZZ = "whizz";
    public static final String BUZZ = "buzz";
    public static final String FIZZ = "fizz";

    static String print(int input, List<Rule> containsRules, List<Rule> timesRules) {
        StringBuilder sb = new StringBuilder();

        containsRules.stream()
                .map(rule -> isContains(input, rule))
                .filter(Optional::isPresent).map(Optional::get)
                .forEach(sb::append);

        if (sb.length() > 0) {
            return sb.toString();
        }

        timesRules.stream()
                .map(rule -> isTimes(input, rule))
                .filter(Optional::isPresent).map(Optional::get)
                .forEach(sb::append);
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
