package com.lzb.container.field;

import jakarta.inject.Inject;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-06 10:16
 * @author mac
 */
@Getter
public class ClassC extends ClassB {

    @Inject
    private ClassB b;


}
