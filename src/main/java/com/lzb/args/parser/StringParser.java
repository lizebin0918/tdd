package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class StringParser extends IntParser implements Parser {

    @Override
    public Object parseValue(String value) {
        return String.valueOf(value);
    }
}
