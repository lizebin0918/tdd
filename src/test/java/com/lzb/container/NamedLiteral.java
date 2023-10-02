package com.lzb.container;

import java.lang.annotation.Annotation;

/**
 * 可行：xxx.withAnnotation(new NamedLiteral())
 * 不可行：xxx.withAnnotation(new @Named())
 * Created on : 2023-09-30 23:01
 * @author lizebin
 */
public record NamedLiteral() implements Named {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Named.class;
    }
}
