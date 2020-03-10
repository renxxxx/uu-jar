package com.oneall.util;

import java.util.HashMap;
import java.util.Map;

public class MapChain<K, V> {

	public Map<K, V> map = null;

	public MapChain(Map<K, V> map) {
		super();
		this.map = map;
	}

	public MapChain() {
		super();
		this.map = new HashMap<K, V>();
	}

	public MapChain<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}

	public MapChain<K, V> putAll(Map map) {
		this.map.putAll(map);
		return this;
	}

	public V get(K key) {
		return map.get(key);
	}
}
