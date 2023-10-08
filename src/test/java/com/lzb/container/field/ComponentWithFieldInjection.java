package com.lzb.container.field;

import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-05 22:36
 * @author mac
 */
public class ComponentWithFieldInjection implements Component {

    @Inject
    private Dependency dependency;

    public Dependency getDependency() {
        return dependency;
    }

}
