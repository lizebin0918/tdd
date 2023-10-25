package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-25 10:42
 * @author mac
 */
public class NotCyclicDependency implements Dependency {

    @Inject
    public NotCyclicDependency(@SkyWalker Dependency dependency) {
    }

}
