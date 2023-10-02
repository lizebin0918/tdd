package com.lzb.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Provider;
import lombok.SneakyThrows;
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

    <Component, ComponentImpl extends Component> void bind(Class<Component> componentClass, Class<ComponentImpl> implementationClass) {
        newComponents.put(componentClass, () -> {
            try {
                return implementationClass.getConstructors()[0].newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
