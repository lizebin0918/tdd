package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * <br/>
 * Created on : 2023-10-25 10:40
 * @author mac
 */
public class SkywalkerDependency implements Dependency {

    @Inject
    public SkywalkerDependency(@Named("ChosenOne") Dependency dependency) {
    }
}
