package com.lzb.container;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-04 11:52
 * @author lizebin
 */
public class DependencyE implements Dependency {

    @Inject
    public DependencyE(DependencyF dependencyF) {

    }

}
