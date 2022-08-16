package com.lzb.di;

/**
 * <br/>
 * Created on : 2022-08-05 09:54
 *
 * @author lizebin
 */
public class DependencyNotFoundException extends RuntimeException {

    private Class<?> component;
    private Class<?> dependency;

    public DependencyNotFoundException(Class<?> dependency) {
        this.dependency = dependency;
    }

    public DependencyNotFoundException(Class<?> component, Class<?> dependency) {
        this.component = component;
        this.dependency = dependency;
    }

    public Class<?> getDependency() {
        return dependency;
    }

    public Class<?> getComponent() {
        return component;
    }
}
