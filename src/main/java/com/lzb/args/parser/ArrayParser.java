package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class ArrayParser implements Parser<String[]> {

    private final IntFunction<String[]> arrayFunction;
    private final String end;

    public ArrayParser(String end, IntFunction<String[]> arrayFunction) {
        this.arrayFunction = arrayFunction;
        this.end = end;
    }

    @Override
    public String[] parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        Predicate<String> notContains = Predicate.not(item -> Objects.equals(end, item));
        return arguments.stream().skip(index + 1L).takeWhile(notContains).toArray(arrayFunction);
    }
}
