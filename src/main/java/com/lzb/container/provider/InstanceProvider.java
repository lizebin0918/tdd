package com.lzb.container.provider;

import java.util.Collections;
import java.util.List;

import com.lzb.container.Context;
import com.lzb.container.ContextProvider;

/**
 * <br/>
 * Created on : 2023-10-05 22:29
 * @author mac
 */
public class InstanceProvider<T> implements ContextProvider<T> {

    private final T instance;

    public InstanceProvider(T instance) {
        this.instance = instance;
    }

    @Override
    public T get(Context context) {
        return instance;
    }

    @Override
    public List<Class<?>> getDependencies() {
        return Collections.emptyList();
    }

}