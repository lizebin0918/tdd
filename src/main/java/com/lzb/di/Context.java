package com.lzb.di;

import jakarta.inject.Provider;

import java.util.HashMap;
import java.util.Map;

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

    private <T> T getComponentType(Class<?> implementation) {
        try {
            return (T) implementation.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementation) {
        providers.put(componentType, (Provider<T>) () -> getComponentType(implementation));
    }
}
