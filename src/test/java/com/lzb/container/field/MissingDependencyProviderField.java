package com.lzb.container.field;

import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class MissingDependencyProviderField implements Component {
    @Inject
    private Provider<Dependency> dependency;
}