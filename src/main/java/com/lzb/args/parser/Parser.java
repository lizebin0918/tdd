package com.lzb.args.parser;

import com.lzb.args.option.Option;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 21:55
 *
 * @author lizebin
 */
public interface Parser<T> {

    String STRING_ARRAY_SIGN = "-g";
    String NUMBER_ARRAY_SIGN = "-d";

    T parse(List<String> arguments, Option option);

}
