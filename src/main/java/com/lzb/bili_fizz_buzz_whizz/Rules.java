package com.lzb.bili_fizz_buzz_whizz;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * <br/>
 * Created on : 2023-08-18 10:15
 * @author mac
 */
public final class Rules {

    public static Rule times(int number, String word) {
        return x -> {
            if (x % number == 0) {
                return Optional.of(word);
            }
            return Optional.empty();
        };
    }

    public static Rule contains(int number, String word) {
        return x -> {
            if (String.valueOf(x).contains(String.valueOf(number))) {
                return Optional.of(word);
            }
            return Optional.empty();
        };
    }

    public static Rule defaultRule() {
        return x -> Optional.of(String.valueOf(x));
    }

    public static Rule anyOf(Rule... rules) {
        return x -> Stream.of(rules)
                .map(rule -> rule.apply(x))
                .filter(Optional::isPresent)
                .map(Optional::get).findAny();
    }

    public static Rule allOf(Rule... rules) {
        StringBuilder result = new StringBuilder();
        return x -> {
            boolean match = Stream.of(rules)
                    .map(rule -> rule.apply(x))
                    .allMatch(resultOpt -> {
                        if (resultOpt.isPresent()) {
                            result.append(resultOpt.get());
                            return true;
                        }
                        return false;
                    });
            return match ? Optional.of(result.toString()) : Optional.empty();
        };
    }

    private Rules() {}

}
