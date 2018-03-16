package com.github.ean244.jrcreator.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Cacher<T> {
	private final Map<Long, T> cacheMap;
	
	public Cacher() {
		this.cacheMap = new ConcurrentHashMap<>(8, 0.9f, 1);
	}
	
	public void cache(long key, T value) {
		cacheMap.put(key, value);
	}
	
	public T getCache(long key) {
		return cacheMap.get(key);
	}
	
	public boolean hasCache(long key) {
		return cacheMap.containsKey(key);
	}
	
	public void update(long key, T value) {
		cacheMap.replace(key, value);
	}
}
