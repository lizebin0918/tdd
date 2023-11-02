package com.lzb.container;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2023-11-02 08:12
 * @author mac
 */
public class PooledProvider<T> implements ComponentProvider<T> {

    public static final int MAX = 2;

    private final List<T> pool = new ArrayList<>();

    private final ComponentProvider<T> provider;

    private int current;

    public PooledProvider(ComponentProvider<T> provider) {
        this.provider = provider;
    }

    @Override
    public T get(Context context) {
        if (pool.size() < MAX) {
            pool.add(provider.get(context));
        }
        return pool.get(current++ % MAX);
    }

    @Override
    public List<ComponentRef> getDependencies() {
        return provider.getDependencies();
    }
}
