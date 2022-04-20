package com.lzb.parser.demo2;

import com.lzb.parser.Parser;

import java.util.function.Function;

/**
 * <br/>
 * Created on : 2022-04-21 07:14
 *
 * @author lizebin
 */
public class SingleValueParser<T> implements Parser {

    Function<String, T> valueParser;

    public SingleValueParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(String s) {
        // 共用业务逻辑
        System.out.println("入参:" + s);
        return valueParser.apply(s);
    }

    public static void main(String[] args) {
        SingleValueParser<String> stringSingleValueParser = new SingleValueParser<>(Function.identity());
        System.out.println(stringSingleValueParser.parse("test"));
    }
}
