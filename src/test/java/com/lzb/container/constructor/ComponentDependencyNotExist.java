package com.lzb.container.constructor;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-04 09:40
 * @author lizebin
 */
public class ComponentDependencyNotExist implements Component {

    @Inject
    public ComponentDependencyNotExist(String s) {

    }

}
