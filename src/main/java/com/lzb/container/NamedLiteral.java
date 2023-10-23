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
        if (this == o) return true;
        if (!(o instanceof NamedLiteral that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
