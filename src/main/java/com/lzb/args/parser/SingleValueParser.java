package com.lzb.args.parser;

import com.lzb.args.exception.TooManyArgumentException;
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
    private final T defaultValue;

    public SingleValueParser(Function<String, T> valueParser, T defaultValue) {
        this.valueParser = valueParser;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index < 0) {
            return null;
        }
        int next = index + 2;
        if (next < arguments.size() && !arguments.get(next).startsWith("-")) {
            throw new TooManyArgumentException(option);
        }
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected T parseValue(String value) {
        return valueParser.apply(value);
    }
}
