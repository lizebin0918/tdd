package com.lzb.container;

import jakarta.inject.Provider;

public class CacheProvider<T> implements Provider<T> {

	/**
	 * 数据
	 */
	private final T data;

	public CacheProvider(Provider<T> provider) {
		data = provider.get();
	}

	@Override
	public T get() {
		return data;
	}

}