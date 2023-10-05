package com.lzb.container.provider;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.lzb.container.Context;
import com.lzb.container.ContextProvider;
import jakarta.inject.Inject;

public class ConstructorInjectProvider<T> implements ContextProvider<T> {

    private final Constructor<?> constructor;

    private final List<Class<?>> dependencies;

    public ConstructorInjectProvider(Class<?> implementationClass) {
        this.constructor = getConstructor(implementationClass);
        this.dependencies = List.of(constructor.getParameterTypes());
    }

    static boolean byIsInjectConstructor(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(Inject.class);
    }

    private static <T, I extends T> Optional<Constructor<?>> getDefaultConstructor(Class<I> implementationClass) {
        try {
            return Optional.of(implementationClass.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static <T, I extends T> Constructor<?> getConstructor(Class<I> implementationClass) {

        List<Constructor<?>> injectConstructors = getInjectConstructors(implementationClass).toList();
        long injectConstructorsCount = injectConstructors.size();
        if (injectConstructorsCount > 1) {
            throw new IllegalArgumentException("不支持多个构造函数");
        }
        return injectConstructors.stream().findFirst().or(() -> getDefaultConstructor(implementationClass)).orElseThrow();
    }

    private static <T, I extends T> Stream<Constructor<?>> getInjectConstructors(Class<I> implementationClass) {
        return Arrays.stream(implementationClass.getConstructors()).filter(ConstructorInjectProvider::byIsInjectConstructor);
    }

    private Object[] getInjectDependencies(Context context) {
        return dependencies.stream()
                .map(context::get)
                .filter(Optional::isPresent)
                .map(Optional::get).toArray();
    }

    @Override
    public T get(Context context) {
        try {
            return (T) constructor.newInstance(getInjectDependencies(context));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Class<?>> getDependencies() {
        return this.dependencies;
    }
}