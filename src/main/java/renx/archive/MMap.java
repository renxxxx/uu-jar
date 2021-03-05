package renx.archive;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MMap<K, V> {

	public Map<K, V> map = null;

	public Set<K> keySet() {
		if (this.map == null)
			return null;
		else {
			return this.map.keySet();
		}
	}

	public static <K, V> MMap<K, V> build(Map<K, V> map) {
		if (map == null)
			return new MMap<K, V>();
		else {
			MMap<K, V> mapp = new MMap<K, V>();
			mapp.map = map;
			return mapp;
		}
	}

	public static <K, V> MMap<K, V> build() {
		return build(null);
	}

	public MMap<K, V> put(K key, V value) {
		if (this.map == null)
			this.map = new LinkedHashMap<K, V>();
		this.map.put(key, value);
		return this;
	}

	public MMap<K, V> putAll(Map map) {
		if (this.map != null) {
			this.map.putAll(map);
		}
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
		return Var.toDecimal(map.get(key));
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

	public List getList(K key) {
		if (map == null)
			return null;
		return (List) map.get(key);
	}

	public Map getMap(K key) {
		if (map == null)
			return null;
		return (Map) map.get(key);
	}

	public boolean isEmpty() {
		if (this.map == null)
			return true;
		else
			return this.map.isEmpty();
	}
}
