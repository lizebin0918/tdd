package com.lzb.container.constructor;

import jakarta.inject.Inject;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-03 15:57
 * @author lizebin
 */
@Getter
public class DependencyD {

    private final String name;

    @Inject
    public DependencyD(String name) {
        this.name = name;
    }
}
