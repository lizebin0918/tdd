package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

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
        return (T) newComponents.get(componentClass)
                .get();
    }

    <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {
        newComponents.put(componentClass, () -> {
            try {
                Constructor<?> constructor = getConstructor(implementationClass);
                Object[] parameters = getInjectDependencies(constructor);
                return constructor.newInstance(parameters);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <T, I extends T> Constructor<?> getConstructor(Class<I> implementationClass) {
        return implementationClass.getConstructors()[0];
    }

    private Object[] getInjectDependencies(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = get(parameterTypes[i]);
        }
        return parameters;
    }
}
