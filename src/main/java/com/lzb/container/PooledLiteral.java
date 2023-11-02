package com.lzb.container;

import java.lang.annotation.Annotation;

/**
 * <br/>
 * Created on : 2023-11-02 08:08
 * @author mac
 */
public record PooledLiteral() implements Pooled {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Pooled.class;
    }
}
