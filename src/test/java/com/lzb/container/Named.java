package com.lzb.container;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <br/>
 * Created on : 2023-09-30 23:05
 * @author lizebin
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Named {

}
