package com.lzb.args.parser;

import com.lzb.args.exception.TooManyArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.lzb.args.parser.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValueParserTest {

    @Test
    public void test_multiple_argument() {
        TooManyArgumentException e = assertThrows(TooManyArgumentException.class, () -> {
            new SingleValueParser<Integer>(Integer::parseInt, 0).parse(List.of("-p", "8000", "8081"), option("p"));
        });
        assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "b"})
    public void test(String argument) {
        System.out.println(argument);
    }

}