package com.lzb.args.parser;

import com.lzb.args.exception.TooManyArgumentException;
import com.lzb.args.option.Option;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <br/>
 * Created on : 2022-04-29 08:48
 *
 * @author lizebin
 */
public class BooleanOptionParserTest {

    /**
     * 多给一个参数
     */
    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentException e = assertThrows(TooManyArgumentException.class, () -> {
            new BooleanParser().parse(List.of("-l", "t"), option("l"));
        });
        assertEquals("l", e.getOption());
    }

    /**
     * 不给参数
     */
    @Test
    public void should_not_accept_extra_no_argument_for_boolean_option() {
        assertFalse(new BooleanParser().parse(Collections.emptyList(), option("l")));
    }

    /**
     * 多给多个参数
     */
    /*@Test
    public void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentException e = assertThrows(TooManyArgumentException.class, () -> {
            new BooleanParser().parse(List.of("-l", "t", "a"), option("l"));
        });
        assertEquals("l", e.getOption());
    }*/

    public static Option option(String value) {

        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };

    }

}
