package com.lzb.parser.demo1;

import com.lzb.parser.Parser;

/**
 * <br/>
 * Created on : 2022-04-21 00:25
 *
 * @author lizebin
 */
public class IntParser implements Parser {

    @Override
    public Integer parse(String s) {
        // 模拟相同逻辑
        s += "-" + "test";
        return Integer.valueOf(s);
    }
}
