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
        // 还能这样转
        providers.put(type, (Provider<T>) () -> instance);
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

    public <ComponentType, ComponentTypeImplementation extends ComponentType> void bind(Class<ComponentType> componentType,
                                                                                        Class<ComponentTypeImplementation> implementation) {
        providers.put(componentType, (Provider<ComponentType>) () -> getComponentType(implementation));
    }
}
