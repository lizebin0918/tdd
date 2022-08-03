package com.lzb.di;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

/**
 * <br/>
 * Created on : 2022-07-31 22:20
 *
 * @author mac
 */
public class Context {

    private Map<Class<?>, Provider<?>> providers = new HashMap<>();

    public <T> void bind(Class<T> type, T instance) {
        providers.put(type, () -> instance);
    }

    public <T> T get(Class<T> type) {
        return (T) providers.get(type).get();
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementation) {

        Constructor<?>[] injectConstructors = stream(implementation.getConstructors()).filter(c -> c.isAnnotationPresent(Inject.class)).toArray(Constructor<?>[]::new);
        if (injectConstructors.length > 1) {
            throw new IllegalComponentException();
        }

        if (injectConstructors.length == 0 && stream(implementation.getConstructors()).noneMatch(c -> c.getParameters().length == 0)) {
            throw new IllegalComponentException();
        }

        providers.put(componentType, () -> {
            try {
                Constructor<?> injectConstructor = getInjectConstructor(implementation);
                Object[] dependencies = stream(injectConstructor.getParameters()).map(p -> get(p.getType())).toArray(Object[]::new);
                return injectConstructor.newInstance(dependencies);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> Constructor<T> getInjectConstructor(Class<T> implementation) {
        Stream<Constructor<?>> constructorStream = stream(implementation.getConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class));
        return (Constructor<T>) constructorStream.findFirst().orElseGet(() -> {
            try {
                return implementation.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
