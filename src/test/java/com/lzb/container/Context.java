package com.lzb.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class Context {

    private static final Map<Class<?>, Object> components = new HashMap<>();
    private static final Map<Class<?>, Class<?>> componentImpls = new HashMap<>();

    <T> void bind(Class<T> componentClass, T instance) {
        components.put(componentClass, instance);
    }

    @SneakyThrows
    <T> T get(Class<T> componentClass) {
        T t = (T) components.get(componentClass);
        if (null != t) {
            return t;
        }
        return (T) componentImpls.get(componentClass).getConstructors()[0].newInstance();
    }

    <Component, ComponentImpl extends Component> void bind(Class<Component> componentClass, Class<ComponentImpl> implementationClass) {
        componentImpls.put(componentClass, implementationClass);
    }
}
