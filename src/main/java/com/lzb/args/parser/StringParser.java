package com.lzb.args.parser;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class StringParser extends IntParser implements Parser {

    public static Parser createStringParser() {
        return new IntParser(String::valueOf);
    }

}
