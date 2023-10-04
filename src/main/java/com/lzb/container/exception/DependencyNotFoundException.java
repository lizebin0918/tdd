package com.lzb.container.exception;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-04 10:05
 * @author lizebin
 */
@Getter
public class DependencyNotFoundException extends RuntimeException {

    private final Class<?> dependencyType;
    private final Class<?> componentType;

    public DependencyNotFoundException(Class<?> dependencyType, Class<?> componentType) {
        this.dependencyType = dependencyType;
        this.componentType = componentType;
    }

}
