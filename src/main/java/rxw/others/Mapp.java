package rxw.others;

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
			return new Mapp<K, V>();
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
		if (this.map == null)
			this.map = new HashMap<K, V>();
		this.map.put(key, value);
		return this;
	}

	public Mapp<K, V> putAll(Map map) {
		if (this.map == null)
			this.map = new HashMap<K, V>();
		this.map.putAll(map);
		return this;
	}

	public V get(K key) {
		if (this.map == null)
			return null;
		return map.get(key);
	}

	public V remove(K key) {
		if (this.map == null)
			return null;
		return map.remove(key);
	}

	public String getString(K key) {
		if (map == null)
			return null;
		return Var.toString(map.get(key));
	}

	public Integer getInteger(K key) {
		if (map == null)
			return null;
		return Var.toInteger(map.get(key));
	}

	public Long getLong(K key) {
		if (map == null)
			return null;
		return Var.toLong(map.get(key));
	}

	public BigDecimal getDecimal(K key) {
		if (map == null)
			return null;
		return Var.toBigDecimal(map.get(key));
	}

	public Float getFloat(K key) {
		if (map == null)
			return null;
		return Var.toFloat(map.get(key));
	}

	public Date getDate(K key) {
		if (map == null)
			return null;
		return Var.toDate(map.get(key));
	}

}
