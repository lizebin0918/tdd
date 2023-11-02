package com.lzb.container;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import jakarta.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <br/>
 * Created on : 2023-11-02 08:09
 * @author mac
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface Pooled {

}
