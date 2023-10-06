package com.lzb.container.method;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-06 15:56
 * @author mac
 */
public class NonParameterMethodComponent {

    public boolean called = false;

    @Inject
    public void install() {
        called = true;
    }

}
