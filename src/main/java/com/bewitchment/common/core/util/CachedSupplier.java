package com.bewitchment.common.core.util;

import java.util.function.Supplier;

public class CachedSupplier<T> implements Supplier<T> {

	private T cachedObject = null;
	private Supplier<T> supplier;

	public CachedSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	@Override
	public T get() {
		if (cachedObject == null) {
			cachedObject = supplier.get();
		}
		return cachedObject;
	}

	public void invalidateCache() {
		cachedObject = null;
	}

}
