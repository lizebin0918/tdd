package com.lzb.args;

import com.lzb.args.option.Option;
import com.lzb.args.parser.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
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
     * <p>
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
        return PARSERS.get(parameter.getType()).parse(arguments, option);
    }

    private static Map<Class<?>, Parser<?>> PARSERS = Map.of(
            boolean.class, new BooleanParser(),
            int.class, new SingleValueParser<>(Integer::parseInt),
            String.class, new SingleValueParser<>(String::valueOf),
            String[].class, new ArrayParser(Parser.NUMBER_ARRAY_SIGN, String[]::new, String::valueOf),
            Integer[].class, new ArrayParser(Parser.STRING_ARRAY_SIGN, Integer[]::new, Integer::valueOf)
    );


}
