package com.lzb.container.method;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Provider;

public class ProviderInjectMethod {

    public Provider<Dependency> dependency;

    @jakarta.inject.Inject
    public void install(Provider<Dependency> dependency) {
        this.dependency = dependency;
    }
}