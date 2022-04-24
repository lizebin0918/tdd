package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;
import java.util.function.Function;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class SingleValueParser<T> implements Parser<T> {

    private final Function<String, T> valueParser;

    public SingleValueParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected T parseValue(String value) {
        return valueParser.apply(value);
    }
}
