package com.inininininin.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Mapp<K, V> {

	public Map<K, V> map = null;

	public Mapp(Map<K, V> map) {
		super();
		this.map = map;
	}

	public Mapp() {
		super();
		this.map = new HashMap<K, V>();
	}

	public Mapp<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}

	public Mapp<K, V> putAll(Map map) {
		this.map.putAll(map);
		return this;
	}

	public V get(K key) {
		return map.get(key);
	}

	public String getString(K key) {
		return Value.toString(map.get(key));
	}

	public Integer getInteger(K key) {
		return Value.toInteger(map.get(key));
	}

	public Long getLong(K key) {
		return Value.toLong(map.get(key));
	}

	public BigDecimal getDecimal(K key) {
		return Value.toBigDecimal(map.get(key));
	}

	public Float getFloat(K key) {
		return Value.toFloat(map.get(key));
	}

	public Date getDate(K key) {
		return Value.toDate(map.get(key));
	}

}
