package com.lzb.args;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
class IntParser implements Parser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return Integer.parseInt(arguments.get(index + 1));
    }
}
