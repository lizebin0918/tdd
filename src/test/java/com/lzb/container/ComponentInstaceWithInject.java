package com.lzb.container;


import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-02 22:37
 * @author lizebin
 */
public class ComponentInstaceWithInject implements Component {

    private final Dependency dependency;

    @Inject
    public ComponentInstaceWithInject(Dependency dependency) {
        this.dependency = dependency;
    }

    Dependency getDependency() {
        return dependency;
    }
}
