package com.lzb.di;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/>
 * Created on : 2022-07-31 22:20
 *
 * @author mac
 */
public class Context {

    private Map<Class<?>, Object> components = new HashMap<>();
    private Map<Class<?>, Class<?>> componentImplementations = new HashMap<>();

    public <ComponentType> void bind(Class<ComponentType> type, ComponentType instance) {
        components.put(type, instance);
    }

    public <ComponentType> ComponentType get(Class<ComponentType> type) {
        if (components.containsKey(type)) {
            return (ComponentType) components.get(type);
        }
        try {
            return (ComponentType) componentImplementations.get(type).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <ComponentType, ComponentTypeImplementation extends ComponentType> void bind(Class<ComponentType> componentType,
                                                                                        Class<ComponentTypeImplementation> implementation) {
        componentImplementations.put(componentType, implementation);
    }
}
