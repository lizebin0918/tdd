package com.lzb.container;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import jakarta.inject.Provider;

public class CacheProvider<T> implements Provider<T> {


    private final Supplier<T> supplier;

    public CacheProvider(Provider<T> provider) {
        supplier = Suppliers.memoize(Suppliers.synchronizedSupplier(provider::get));
    }

    @Override
    public T get() {
        return supplier.get();
    }

}