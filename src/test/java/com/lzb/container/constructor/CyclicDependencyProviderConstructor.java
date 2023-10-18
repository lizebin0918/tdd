package com.lzb.container.constructor;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

/**
 * <br/>
 * Created on : 2023-10-18 10:07
 * @author lizebin
 */
public class CyclicDependencyProviderConstructor implements Dependency {

    @Inject
    public CyclicDependencyProviderConstructor(Provider<Component> dependency) {
    }

}
