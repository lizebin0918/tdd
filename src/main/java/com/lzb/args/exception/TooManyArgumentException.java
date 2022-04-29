package com.lzb.args.exception;

import com.lzb.args.option.Option;

/**
 * <br/>
 * Created on : 2022-04-29 08:46
 *
 * @author lizebin
 */
public class TooManyArgumentException extends RuntimeException {

    public TooManyArgumentException(String message) {
        super(message);
    }

    private Option option;

    public TooManyArgumentException(Option option) {
        this.option = option;
    }

    public String getOption() {
        return option.value();
    }
}
