package com.oneall.util;

import java.util.Map;

public class ChainMap<K, V> {

	private Map<K, V> map = null;

	public ChainMap(Map<K, V> map) {
		super();
		this.map = map;
	}

	public ChainMap<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}

	public Map<K, V> appear() {
		return map;
	}
}
