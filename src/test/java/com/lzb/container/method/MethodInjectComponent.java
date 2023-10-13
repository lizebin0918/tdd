package com.lzb.container.method;

import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-06 15:37
 * @author mac
 */
public class MethodInjectComponent implements Component {

    private Dependency dependency;

    @Inject
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public Dependency getDependency() {
        return dependency;
    }
}
