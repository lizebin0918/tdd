package com.lzb.container.method;

import jakarta.inject.Inject;

/**
 * <br/>
 * Created on : 2023-10-06 15:56
 * @author mac
 */
public class SubNonParameterMethodComponent extends NonParameterMethodComponent {

    public boolean subCalled = false;

    @Inject
    public void installAnother() {
        subCalled = true;
    }

}
