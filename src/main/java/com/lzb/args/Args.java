package com.lzb.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
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
     * @param arguments
     * @param parameter
     * @param <T>
     * @return
     */
    private static Object parseObject(List<String> arguments, Parameter parameter) {
        Option option = parameter.getAnnotation(Option.class);
        String optionValue = option.value();
        Object value = null;
        if (parameter.getType() == boolean.class) {
            value = arguments.contains("-" + optionValue);
        }
        if (parameter.getType() == int.class) {
            int index = arguments.indexOf("-" + optionValue);
            value = Integer.parseInt(arguments.get(index + 1));
        }
        if (parameter.getType() == String.class) {
            int index = arguments.indexOf("-" + optionValue);
            value = arguments.get(index + 1);
        }
        return value;
    }

}
