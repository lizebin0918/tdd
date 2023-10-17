package com.lzb.container.constructor;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

/**
 * <br/>
 * Created on : 2023-10-17 23:15
 * @author mac
 */
public class MissingDependencyProviderConstructor implements Component {
    @Inject
    public MissingDependencyProviderConstructor(Provider<Dependency> dependency) {
    }
}
