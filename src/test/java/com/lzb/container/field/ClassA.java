package com.lzb.container.field;

import jakarta.inject.Inject;
import lombok.Getter;

/**
 * ClassA和ClassB的属性发生循环依赖
 */
@Getter
public class ClassA {

    @Inject
    private ClassC c;

}
