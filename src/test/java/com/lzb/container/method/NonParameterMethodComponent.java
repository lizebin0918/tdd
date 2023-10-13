package com.lzb.container.method;

import com.lzb.container.constructor.Component;
import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-06 15:56
 * @author mac
 */
public class NonParameterMethodComponent implements Component {

    public boolean called = false;

    @Inject
    public void install() {
        called = true;
    }

}
