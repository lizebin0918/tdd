package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class IntParser implements Parser {

    Function<String, Object> valueParser = Integer::parseInt;

    public IntParser(Function<String, Object> valueParser) {
        this.valueParser = valueParser;
    }

    public IntParser() {}

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {
        return valueParser.apply(value);
    }
}
