package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public record ArrayParser(String end,
                          IntFunction<Object[]> arrayFunction,
                          Function<String, Object> mapper) implements Parser<Object[]> {

    @Override
    public Object[] parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return arguments.stream().skip(index + 1L).takeWhile(Predicate.not(end::equals)).map(mapper).toArray(arrayFunction);
    }
}
