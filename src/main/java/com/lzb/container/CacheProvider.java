package com.lzb.container;

import java.util.concurrent.atomic.AtomicBoolean;

import jakarta.inject.Provider;

public class CacheProvider<T> implements Provider<T> {

	private static final boolean DONE_DEFAULT_VALUE = false;
	/**
	 * 数据
	 */
	private final AtomicBoolean done = new AtomicBoolean(DONE_DEFAULT_VALUE);

	private final Provider<T> provider;

	private volatile T data;

	public CacheProvider(Provider<T> provider) {
		this.provider = provider;
	}

	@Override
	public T get() {
		if (done.compareAndSet(DONE_DEFAULT_VALUE, true)) {
			data = provider.get();
		}
		return data;
	}

}