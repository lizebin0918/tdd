package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-23 08:34
 * @author mac
 */
@Getter
public class QualifierInjectConstructor {
    
    private Dependency dependency;
    @Inject
    public QualifierInjectConstructor(@Named("dependency") Dependency dependency) {
        this.dependency = dependency;
    }

}
