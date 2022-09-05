package com.lzb.di;

/**
 * <br/>
 * Created on : 2022-09-05 22:21
 *
 * @author mac
 */
public interface Context {


    <T> T get(Class<T> componentClass);
}
