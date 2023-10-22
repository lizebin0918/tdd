package com.lzb.container.provider;

import java.util.Collections;
import java.util.List;

import com.lzb.container.ComponentProvider;
import com.lzb.container.ComponentRef;
import com.lzb.container.Context;

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
    public List<ComponentRef> getDependencies() {
        return Collections.emptyList();
    }

}
