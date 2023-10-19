package com.lzb.container.provider;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.lzb.container.Context;
import com.lzb.container.ComponentProvider;

/**
 * <br/>
 * Created on : 2023-10-05 22:29
 * @author mac
 */
public class InstanceProvider<T> implements ComponentProvider<T> {

    private final T instance;

    public InstanceProvider(T instance) {
        this.instance = instance;
    }

    @Override
    public T get(Context context) {
        return instance;
    }

    @Override
    public List<Type> getDependencies() {
        return Collections.emptyList();
    }

}
