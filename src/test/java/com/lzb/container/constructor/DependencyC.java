package com.lzb.container.constructor;


import jakarta.inject.Inject;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-03 09:13
 * @author lizebin
 */
@Getter
public class DependencyC {

    public final DependencyB dependencyB;
    public final String s;

    @Inject
    public DependencyC(DependencyB dependencyB, String s) {
        this.dependencyB = dependencyB;
        this.s = s;
    }
}
