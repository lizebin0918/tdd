package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class StringArrayParser implements Parser<String[]> {

    @Override
    public String[] parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        Predicate<String> notContains = Predicate.not(item -> Objects.equals(NUMBER_ARRAY_SIGN, item));
        return arguments.stream().skip(index + 1L).takeWhile(notContains).toArray(String[]::new);
    }
}
