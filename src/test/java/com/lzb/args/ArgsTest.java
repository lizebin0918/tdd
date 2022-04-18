package com.lzb.args;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {

    @Before
    public void setUp() throws Exception {
    }

    // happy path:正常边界
    // 单个参数解析
    // TODO:lizebin l-Boolean
    @Test
    public void test_parse_l_true() {
        String input = "-l";
        Args args = Args.parse(input);
        assertTrue(args.isLogging());
    }

    @Test
    public void test_parse_l_false() {
        String emptyString = "";
        Args args = Args.parse(emptyString);
        assertFalse(args.isLogging());
    }

    // TODO:lizebin p#-
    // TODO:lizebin d*

    // 多个参数解析
    // TODO:lizebin l,p#,d*

}