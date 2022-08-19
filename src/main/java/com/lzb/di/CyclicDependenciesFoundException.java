package com.lzb.di;

import java.util.HashSet;
import java.util.Set;

/**
 * <br/>
 * Created on : 2022-08-05 10:19
 *
 * @author lizebin
 */
public class CyclicDependenciesFoundException extends RuntimeException {

    private Set<Class<?>> components = new HashSet<>();

    public CyclicDependenciesFoundException(Class<?> component) {
        components.add(component);
    }

    /**
     * 还能这样写....自己作为参数回传，直接访问私有属性
     *
     * @param component
     * @param e
     */
    public CyclicDependenciesFoundException(Class<?> component, CyclicDependenciesFoundException e) {
        components.add(component);
        components.addAll(e.components);
    }

    public Class<?>[] getComponents() {
        return components.toArray(Class[]::new);
    }
}
