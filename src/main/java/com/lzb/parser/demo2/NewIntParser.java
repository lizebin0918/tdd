package com.lzb.parser.demo2;

import com.lzb.parser.demo1.StringParser;

import java.util.function.Function;

/**
 * feat:通过函数式成员变量，实现策略委托<br/>
 * Created on : 2022-04-21 00:29
 *
 * @author lizebin
 */
public class NewIntParser extends StringParser {

    public NewIntParser() {
        this.valueParser = Integer::valueOf;
    }

    @Override
    public Object parse(String s) {
        return this.valueParser.apply(s);
    }
}
