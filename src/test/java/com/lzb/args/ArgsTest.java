package com.lzb.args;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ArgsTest {

    @Before
    public void setUp() throws Exception {
    }

    ///////////////////////////////////////////////////////////////////////////
    // 正常情况
    ///////////////////////////////////////////////////////////////////////////

    // 单个参数解析
    // TODO:lizebin =-l->true/false
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

    // TODO:lizebin =-p 8080
    @Test
    public void test_parse_p_is_8080() {
        String input = "-p 8080";
        Args args = Args.parse(input);
        assertEquals(8080, args.getPort().intValue());
    }

    // TODO:lizebin =-d 1
    @Test
    public void test_parse_d_single_param() {
        String input = "-d 1";
        Args args = Args.parse(input);
        assertTrue(args.getDirs().length > 0);
        assertEquals("1", args.getDirs()[0]);
    }

    // TODO:lizebin =-d 1 2 3 a b c
    @Test
    public void test_parse_d_multiple_param() {
        String input = "-d 1 2 3 a b c";
        Args args = Args.parse(input);
        assertTrue(args.getDirs().length > 0);
        assertArrayEquals(new String[]{"1", "2", "3", "a", "b", "c"}, args.getDirs());
    }

    // 多个参数解析
    // TODO:lizebin l,p#,d*
    @Test
    public void test_parse_l_p_d_param() {
        String input = "-l -p 90 -d a";
        Args args = Args.parse(input);
        assertTrue(args.isLogging());
        assertEquals(90, args.getPort().intValue());
        assertArrayEquals(new String[]{"a"}, args.getDirs());
    }

    @Test
    public void test_parse_l_p_d_disorder_param() {
        String input = "-l -d a -p 90";
        Args args = Args.parse(input);
        assertTrue(args.isLogging());
        assertEquals(90, args.getPort().intValue());
        assertArrayEquals(new String[]{"a"}, args.getDirs());
    }

    @Test
    public void test_parse_l_p_d_disorder_dir() {
        String input = "-l -d a -p 90";
        Args args = Args.parse(input);
        assertArrayEquals(new String[]{"a"}, args.getDirs());
    }

}