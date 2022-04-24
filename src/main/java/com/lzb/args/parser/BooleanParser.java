package com.lzb.args.parser;

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
        return arguments.contains("-" + option.value());
    }
}
