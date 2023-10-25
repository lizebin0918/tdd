package com.lzb.container.qualifier;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.Test;

public record TestLiteral() implements Test {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Test.class;
    }
}