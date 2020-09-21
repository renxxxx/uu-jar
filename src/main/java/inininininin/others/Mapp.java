package inininininin.others;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Mapp<K, V> {

	public Map<K, V> map = null;

	public Set<K> keySet() {
		if (this.map == null)
			return null;
		else {
			return this.map.keySet();
		}
	}

	public static <K, V> Mapp<K, V> instance(Map<K, V> map) {
		if (map == null)
			return instance();
		else {
			Mapp<K, V> mapp = new Mapp<K, V>();
			mapp.map = map;
			return mapp;
		}
	}

	public static <K, V> Mapp<K, V> instance() {
		Mapp<K, V> mapp = new Mapp<K, V>();
		mapp.map = new HashMap<K, V>();
		return mapp;
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

	public V remove(K key) {
		return map.remove(key);
	}

	public String getString(K key) {
		if (map == null)
			return null;
		return Value.toString(map.get(key));
	}

	public Integer getInteger(K key) {
		if (map == null)
			return null;
		return Value.toInteger(map.get(key));
	}

	public Long getLong(K key) {
		if (map == null)
			return null;
		return Value.toLong(map.get(key));
	}

	public BigDecimal getDecimal(K key) {
		if (map == null)
			return null;
		return Value.toBigDecimal(map.get(key));
	}

	public Float getFloat(K key) {
		if (map == null)
			return null;
		return Value.toFloat(map.get(key));
	}

	public Date getDate(K key) {
		if (map == null)
			return null;
		return Value.toDate(map.get(key));
	}

}
