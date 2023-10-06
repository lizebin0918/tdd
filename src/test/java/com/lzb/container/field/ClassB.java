package com.lzb.container.field;

import jakarta.inject.Inject;
import lombok.Getter;

@Getter
public class ClassB extends ClassA {
    @Inject
    private ClassA a;

}