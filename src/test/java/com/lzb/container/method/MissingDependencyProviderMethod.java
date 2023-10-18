package com.lzb.container.method;

import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class MissingDependencyProviderMethod implements Component {
    @Inject
    public void install(Provider<Dependency> dependency) {

    }
}