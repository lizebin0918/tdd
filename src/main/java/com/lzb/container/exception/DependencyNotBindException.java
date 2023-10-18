package com.lzb.container.exception;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-04 10:05
 * @author lizebin
 */
@Getter
public class DependencyNotBindException extends RuntimeException {

    private final Class<?> dependencyType;
    private final Class<?> componentType;

    public DependencyNotBindException(Class<?> componentType, Class<?> dependencyType) {
        this.dependencyType = dependencyType;
        this.componentType = componentType;
    }

}
