package com.lzb.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class Context {

    private static final Map<Class<?>, Object> components = new HashMap<>();

    <T> void bind(Class<T> componentClass, T instance) {
        components.put(componentClass, instance);
    }

    <T> T get(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    <Component, ComponentImpl extends Component> void bind(Class<Component> componentClass, Class<ComponentImpl> implementationClass) {
        Constructor<?> constructor = implementationClass.getConstructors()[0];
        try {
            components.put(implementationClass, constructor.newInstance(null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
