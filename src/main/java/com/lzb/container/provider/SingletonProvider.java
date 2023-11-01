package com.lzb.container.provider;

import java.util.List;
import java.util.Objects;

import com.lzb.container.ComponentProvider;
import com.lzb.container.ComponentRef;
import com.lzb.container.Context;

/**
 * <br/>
 * Created on : 2023-10-31 10:23
 * @author lizebin
 */
public class SingletonProvider<T> implements ComponentProvider<T> {

    private final ComponentProvider<T> componentProvider;
    private T instance;
    public SingletonProvider(ComponentProvider<T> componentProvider) {
        this.componentProvider = componentProvider;
    }

    @Override
    public T get(Context context) {
        if (Objects.isNull(instance)) {
            instance = componentProvider.get(context);
        }
        return instance;
    }

    @Override
    public List<ComponentRef> getDependencies() {
        return componentProvider.getDependencies();
    }
}
