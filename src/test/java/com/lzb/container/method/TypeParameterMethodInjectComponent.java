package com.lzb.container.method;

import jakarta.inject.Inject;

/**
 * 泛型方法注入<br/>
 * Created on : 2023-10-07 10:27
 * @author lizebin
 */
public class TypeParameterMethodInjectComponent {
    @Inject
    <T> void install() {

    }

}
