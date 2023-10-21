package com.lzb.container;

import java.lang.annotation.Annotation;

public record Component(Class<?> type, Annotation annotation) {
}
