package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;

public class ConstructorInjectProvider<T> implements ContextProvider<T> {

    private final Constructor<T> constructor;

    private final List<Class<?>> dependencies;

    public ConstructorInjectProvider(Constructor<T> constructor) {
        this.constructor = constructor;
        this.dependencies = List.of(constructor.getParameterTypes());
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
            return constructor.newInstance(getInjectDependencies(context));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Class<?>> getDependencies() {
        return this.dependencies;
    }
}