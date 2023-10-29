package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * <br/>
 * Created on : 2023-10-29 15:44
 * @author mac
 */
public class QualifierInjectField {

    @Inject
    @Named("fieldA")
    private Dependency dependency;

}
