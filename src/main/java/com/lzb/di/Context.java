package com.lzb.di;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementation) {
        Constructor<?> injectConstructor = getInjectConstructor(implementation);
        providers.put(componentType, () -> {
            try {
                Object[] dependencies = stream(injectConstructor.getParameters()).map(p -> get(p.getType()).orElseThrow(DependencyNotFoundException::new)).toArray(Object[]::new);
                return injectConstructor.newInstance(dependencies);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> Constructor<T> getInjectConstructor(Class<T> implementation) {
        List<Constructor<?>> injectConstructors = stream(implementation.getConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class)).toList();

        if (injectConstructors.size() > 1) throw new IllegalComponentException();

        return (Constructor<T>) injectConstructors.stream().findFirst().orElseGet(() -> {
            try {
                return implementation.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalComponentException();
            }
        });
    }

    public <T> Optional<T> get(Class<T> componentClass) {
        return Optional.ofNullable(providers.get(componentClass)).map(provider -> (T) provider.get());
    }
}
