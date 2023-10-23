package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * <br/>
 * Created on : 2023-10-23 08:34
 * @author mac
 */
public class QualifierInjectConstructor {

    @Inject
    //public QualifierInjectConstructor(@SkyWalker String a) {
    public QualifierInjectConstructor(@Named("dependency") Dependency dependency) {
    }

}
