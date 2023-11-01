package com.lzb.container.scope;

import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

/**
 * <br/>
 * Created on : 2023-11-01 10:02
 * @author lizebin
 */
@Singleton
public class MissingProviderDependencyScoped implements Component {

    @Inject
    private Provider<Dependency> dependency;

}
