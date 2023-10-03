package com.lzb.container;


import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-03 09:13
 * @author lizebin
 */
public class DependencyC {

    public final DependencyB dependencyB;

    @Inject
    public DependencyC(DependencyB dependencyB) {
        this.dependencyB = dependencyB;
    }
}
