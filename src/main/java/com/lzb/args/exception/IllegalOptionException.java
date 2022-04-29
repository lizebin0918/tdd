package com.lzb.args.exception;

import com.lzb.args.option.Option;

/**
 * <br/>
 * Created on : 2022-04-29 22:38
 *
 * @author lizebin
 */
public class IllegalOptionException extends RuntimeException {

    public IllegalOptionException(String message) {
        super(message);
    }

    private Option option;

    public IllegalOptionException(Option option) {
        this.option = option;
    }

    public String getOption() {
        return option.value();
    }

}
