package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class Context {

    private static final Map<Class<?>, Provider<?>> newComponents = new HashMap<>();

    <T> void bind(Class<T> componentClass, T instance) {
        newComponents.put(componentClass, () -> instance);
    }

    <T> T get(Class<T> componentClass) {
        return (T) newComponents.get(componentClass).get();
    }

    <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {

        if(getInjectConstructors(implementationClass).count() > 1) {
            throw new IllegalArgumentException("不支持多个构造函数");
        }

        newComponents.put(componentClass, new CacheProvider<>(() -> {
            try {
                Constructor<?> constructor = getConstructor(implementationClass);
                Object[] dependencies = getInjectDependencies(constructor);
                return constructor.newInstance(dependencies);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private <T, I extends T> Constructor<?> getConstructor(Class<I> implementationClass) {
        return getInjectConstructors(implementationClass)
                .findFirst()
                // 返回默认构造函数
                .orElseGet(() -> {
                    try {
                        return implementationClass.getConstructor();
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private <T, I extends T> Stream<Constructor<?>> getInjectConstructors(Class<I> implementationClass) {
        return Arrays.stream(implementationClass.getConstructors()).filter(this::byIsInjectConstructor);
    }

    private boolean byIsInjectConstructor(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(Inject.class);
    }

    private Object[] getInjectDependencies(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes()).map(this::get).toArray();
    }
}
