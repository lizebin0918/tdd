package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * <br/>
 * Created on : 2023-10-29 16:29
 * @author mac
 */
public class MultiQualifierInjectField {

    @Inject
    @Named("dependency")
    @SkyWalker
    Dependency dependency;

}
