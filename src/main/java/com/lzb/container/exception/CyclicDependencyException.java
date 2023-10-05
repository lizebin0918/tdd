package com.lzb.container.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-04 11:53
 * @author lizebin
 */
@Getter
public class CyclicDependencyException extends RuntimeException {

    private final Set<Class<?>> components = new HashSet<>();

    public CyclicDependencyException(Class<?> componentType, CyclicDependencyException e) {
        this.components.add(componentType);
        this.components.addAll(e.getComponents());
    }

    public CyclicDependencyException(Class<?> componentType) {
        this.components.add(componentType);
    }

    public CyclicDependencyException(Collection<Class<?>> componentTypes) {
        this.components.addAll(componentTypes);
    }

}
