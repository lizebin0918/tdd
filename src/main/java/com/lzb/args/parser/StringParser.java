package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class StringParser implements Parser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    private String parseValue(String value) {
        return String.valueOf(value);
    }
}
