package com.lzb.args;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 21:55
 *
 * @author lizebin
 */
public interface Parser {

    Object parse(List<String> arguments, Option option);

}
