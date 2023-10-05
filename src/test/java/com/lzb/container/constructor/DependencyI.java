package com.lzb.container.constructor;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-04 20:58
 * @author mac
 */
public class DependencyI implements Dependency {

    @Inject
    public DependencyI(DependencyH h) {

    }

}
