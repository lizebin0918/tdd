package com.lzb.args.parser;

import com.lzb.args.exception.TooManyArgumentException;
import com.lzb.args.option.Option;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 23:11
 *
 * @author lizebin
 */
public class BooleanParser implements Parser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index < 0) {
            return false;
        }
        int next = index + 1;
        if (next < arguments.size() && !arguments.get(next).startsWith("-")) {
            throw new TooManyArgumentException(option);
        }
        return index >= 0;
    }
}
