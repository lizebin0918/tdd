package com.lzb.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * <br/>
 * Created on : 2022-04-23 15:14
 *
 * @author lizebin
 */
public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {

        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
        // 应该是用到才声明
        /*Parameter parameter = constructor.getParameters()[0];
        Option option = parameter.getAnnotation(Option.class);
        String optionValue = option.value();*/
        List<String> arguments = List.of(args);

        try {
            Object[] values = Stream.of(constructor.getParameters()).map(p -> parseObject(arguments, p)).toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重构的原则："符合最少知道原则"
     *
     * 开始重构
     * 1.抽取方法
     * 2.所有方法都有共同的入参，可以抽取公共接口
     * 3.每个方法的返回值，可以直接inline变量
     * 4.新建BooleanParser内部类，引用外部方法，再inline进来，外部方法可以删掉
     * 5.提取同类型Parser本地变量，把所有子类的变量，重构成对应的Parser类型
     *
     * @param arguments
     * @param parameter
     * @param <T>
     * @return
     */
    private static Object parseObject(List<String> arguments, Parameter parameter) {
        Option option = parameter.getAnnotation(Option.class);
        Object value = null;
        if (parameter.getType() == boolean.class) {
            Parser parser = new BooleanParser();
            value = parser.parse(arguments, option);
        }
        if (parameter.getType() == int.class) {
            Parser parser = new IntParser();
            value = parser.parse(arguments, option);
        }
        if (parameter.getType() == String.class) {
            Parser parser = new StringParser();
            value = parser.parse(arguments, option);
        }
        if (parameter.getType().componentType() == String.class) {
            Parser parser = new StringArrayParser();
            value = parser.parse(arguments, option);
        }
        if (parameter.getType().componentType() == int.class) {
            Parser parser = new IntArrayParser();
            value = parser.parse(arguments, option);
        }
        return value;
    }

    static class IntArrayParser implements Parser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            Predicate<String> notContains = Predicate.not(item -> Objects.equals("-l", item));
            return arguments.stream().skip(index + 1L).takeWhile(notContains).mapToInt(Integer::valueOf).toArray();
        }
    }

    static class StringArrayParser implements Parser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            Predicate<String> notContains = Predicate.not(item -> Objects.equals("-d", item));
            return arguments.stream().skip(index + 1L).takeWhile(notContains).toArray(String[]::new);
        }
    }

    static class StringParser implements Parser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return arguments.get(index + 1);
        }
    }

    static class IntParser implements Parser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return Integer.parseInt(arguments.get(index + 1));
        }
    }

    static class BooleanParser implements Parser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains("-" + option.value());
        }
    }

}
