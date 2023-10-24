package com.lzb.container.qualifier;

import java.lang.annotation.Annotation;

/**
 * <br/>
 * Created on : 2023-10-24 08:50
 * @author mac
 */
public class SkyWalkerLiteral implements SkyWalker {

    @Override
    public Class<? extends Annotation> annotationType() {
        return SkyWalker.class;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SkyWalker;
    }
}
