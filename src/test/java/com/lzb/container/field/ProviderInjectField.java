package com.lzb.container.field;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class ProviderInjectField {

    @Inject
    public Provider<Dependency> dependency;

}