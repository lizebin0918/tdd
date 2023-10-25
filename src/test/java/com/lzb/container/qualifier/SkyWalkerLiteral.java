package com.lzb.container.qualifier;

import java.lang.annotation.Annotation;

/**
 * <br/>
 * Created on : 2023-10-24 08:50
 * @author mac
 */
public record SkyWalkerLiteral() implements SkyWalker {

    @Override
    public Class<? extends Annotation> annotationType() {
        return SkyWalker.class;
    }

    /*@Override
    public boolean equals(Object o) {
        if (o instanceof Named named) {
            return Objects.equals(value, named.value());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return "value".hashCode() * 127 ^ value.hashCode();
    }*/
}
