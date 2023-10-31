package com.lzb.container;

import java.lang.annotation.Annotation;

import jakarta.inject.Singleton;

public record SingletonLiteral() implements Singleton {
    @Override
    public Class<? extends Annotation> annotationType() {
        return Singleton.class;
    }
}
