package com.giveup;

import java.util.Map;

public class MapChain<K, V> {

	private Map<K, V> map = null;

	public MapChain(Map<K, V> map) {
		super();
		this.map = map;
	}

	public MapChain<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}

	public Map<K, V> appear() {
		return map;
	}
}
