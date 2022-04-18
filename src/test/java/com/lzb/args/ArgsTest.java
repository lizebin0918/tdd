package com.lzb.args;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {

    @Before
    public void setUp() throws Exception {
    }

    // 单个参数解析
    // TODO:lizebin l
    @Test
    public void test_parse_l() {
        Args args = new Args('l');
        assertFalse(args.getBoolean('l'));
    }

    // TODO:lizebin p#
    // TODO:lizebin d*

    // 多个参数解析
    // TODO:lizebin l,p#,d*

}