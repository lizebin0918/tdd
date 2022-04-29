package com.lzb.args;

import com.alibaba.fastjson.JSON;
import com.lzb.args.option.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * 先设计API
 * 先写一个失败的测试用例，通过之后，再写这个测试用例的反例
 *
 * <br/>
 * Created on : 2022-04-23 13:35
 *
 * @author lizebin
 */
public class ArgsTest {

    // -l -p 8080 -d /usr/logs
    // 实际是一个字符数组: [-l], [-p,8080], [-d, /usr/logs]，通过index来获取
    // 任务分解
    /*
    - happy path
    - 单参数解析
        TODO:- Boolean(done)
        TODO:- Integer
        TODO:- String
    - 多参数解析
        TODO:- -l -p 8080 -d /usr/logs
    - sad path
    - 异常参数
    - default value
        TODO:- Boolean false
        TODO:- Integer 0
        TODO:- Sstring ""
    */

    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption parse = Args.parse(BooleanOption.class, "-l");
        assertTrue(parse.logging());
    }

    @Test
    public void should_set_boolean_option_to_true_if_flag_not_present() {
        BooleanOption parse = Args.parse(BooleanOption.class, "");
        assertFalse(parse.logging());
    }

    @Test
    public void should_set_integer_option_to_8080() {
        IntOption parse = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, parse.port());
    }

    @Test
    public void should_set_string_option() {
        String dir = "/usr/logs";
        StringOption parse = Args.parse(StringOption.class, "-d", dir);
        assertEquals(dir, parse.dir());
    }

    @Test
    public void should_example_1() {
        MultipleOption multipleOption = Args.parse(MultipleOption.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(multipleOption.logging());
        Assert.assertEquals(8080, multipleOption.port());
        Assert.assertEquals("/usr/logs", multipleOption.directory());
    }

    @Test
    public void should_example_2() {
        ListOption options = Args.parse(ListOption.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "3");
        Assert.assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, options.decimals());
    }

    @Test
    public void should_example_3() {
        ListOption options = Args.parse(ListOption.class, "-d", "1", "2", "3", "-g", "this", "is", "a", "list");
        Assert.assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, options.decimals());
    }

}
