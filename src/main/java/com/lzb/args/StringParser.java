package com.lzb.args;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
class StringParser implements Parser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return arguments.get(index + 1);
    }
}
