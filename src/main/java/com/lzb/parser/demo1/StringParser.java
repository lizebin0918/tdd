package com.lzb.parser.demo1;

import com.lzb.parser.Parser;

import java.util.function.Function;

/**
 * <br/>
 * Created on : 2022-04-21 00:24
 *
 * @author lizebin
 */
public class StringParser implements Parser {

    protected Function<String, Object> valueParser = String::valueOf;

    public StringParser() {}

    public StringParser(Function<String, Object> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public Object parse(String s) {
        // 模拟相同逻辑
        s += "-" + "test";
        return valueParser.apply(s);
    }
}
