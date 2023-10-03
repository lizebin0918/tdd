package com.lzb.container;


import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-03 09:13
 * @author lizebin
 */
public class DependencyB {

    public final DependencyA dependencyA;

    @Inject
    public DependencyB(DependencyA dependencyA) {
        this.dependencyA = dependencyA;
    }
}
