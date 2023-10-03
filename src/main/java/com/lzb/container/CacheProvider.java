package com.lzb.container;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.inject.Provider;

public class CacheProvider<T> implements Provider<T> {

	/**
	 * 数据
	 */
	private final AtomicReference<T> data = new AtomicReference<>();

	private final Provider<T> provider;

	public CacheProvider(Provider<T> provider) {
		this.provider = provider;
	}

	@Override
	public T get() {
		return data.updateAndGet(v -> Objects.isNull(v) ? provider.get() : v);
	}

}