package com.lzb.container;

import java.lang.annotation.Annotation;
import java.util.Objects;

import jakarta.inject.Named;

/**
 * 可行：xxx.withAnnotation(new NamedLiteral())
 * 不可行：xxx.withAnnotation(new @Named())
 * Created on : 2023-09-30 23:01
 * @author lizebin
 */
public record NamedLiteral(String value) implements Named {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Named.class;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Named named) {
            return Objects.equals(value, named.value());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return "value".hashCode() * 127 ^ value.hashCode();
    }

}
