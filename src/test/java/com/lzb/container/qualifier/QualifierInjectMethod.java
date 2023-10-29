package com.lzb.container.qualifier;

import com.lzb.container.constructor.Dependency;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-29 15:35
 * @author mac
 */
@Getter
public class QualifierInjectMethod {

    private Dependency dependency;

    @Inject
    public void install(@Named("ChosenOne") Dependency dependency) {
        this.dependency = dependency;
    }

}
