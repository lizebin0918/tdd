package com.lzb.container.constructor;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-04 11:52
 * @author lizebin
 */
public class DependencyF implements Dependency {

    @Inject
    public DependencyF(DependencyE dependencyE) {

    }

}
