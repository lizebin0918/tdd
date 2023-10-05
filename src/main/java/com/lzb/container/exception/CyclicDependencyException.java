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
    public CyclicDependencyException(Collection<Class<?>> componentTypes) {
        this.components.addAll(componentTypes);
    }

}
