package com.lzb.container.exception;

import com.lzb.container.Component;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-04 10:05
 * @author lizebin
 */
@Getter
public class DependencyNotBindException extends RuntimeException {

    private Class<?> dependencyType;
    private Class<?> componentType;
    private Component dependencyComponent;
    private Component componentComponent;

    public DependencyNotBindException(Class<?> componentType, Class<?> dependencyType) {
        this.dependencyType = dependencyType;
        this.componentType = componentType;
    }

    public DependencyNotBindException(Component componentComponent, Component dependencyComponent) {
        this.dependencyComponent = dependencyComponent;
        this.componentComponent = componentComponent;
    }
}
