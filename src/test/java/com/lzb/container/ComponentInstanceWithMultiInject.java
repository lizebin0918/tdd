package com.lzb.container;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-04 08:05
 * @author lizebin
 */
public class ComponentInstanceWithMultiInject implements Component {

    @Inject
    public ComponentInstanceWithMultiInject(String s, Integer i) {

    }

    @Inject
    public ComponentInstanceWithMultiInject(String s) {

    }

}
