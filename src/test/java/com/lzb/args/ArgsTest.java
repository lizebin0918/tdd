package com.lzb.args;

import com.lzb.args.exception.IllegalOptionException;
import com.lzb.args.option.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
        assertEquals(8080, multipleOption.port());
        assertEquals("/usr/logs", multipleOption.directory());
    }

    @Test
    public void should_example_2() {
        ListOption options = Args.parse(ListOption.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "3");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new Integer[]{1, 2, 3}, options.decimals());
    }

    @Test
    public void should_example_3() {
        ListOption options = Args.parse(ListOption.class, "-d", "1", "2", "3", "-g", "this", "is", "a", "list");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new Integer[]{1, 2, 3}, options.decimals());
    }

    @Test
    public void should_present_option() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class, () -> Args.parse(OptionWithoutAnnotation.class, "-a", "-p", "8080"));
        assertEquals("参数非法", e.getMessage());
    }

    record OptionWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String dir) {

    }

    @Nested
    class NestedTest {

        @Test
        public void test() {
            System.out.println("this is a nested test");
        }

    }

}
