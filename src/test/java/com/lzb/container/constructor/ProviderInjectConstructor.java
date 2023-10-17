package com.lzb.container.constructor;

import jakarta.inject.Provider;

public class ProviderInjectConstructor {

    public Provider<Dependency> dependency;

    @jakarta.inject.Inject
    public ProviderInjectConstructor(Provider<Dependency> dependency) {
        this.dependency = dependency;
    }
}