package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-29 15:44
 * @author mac
 */
@Getter
public class QualifierInjectField {

    @Inject
    @Named("fieldA")
    private Dependency dependency;

}
