package rxw.mess;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class LinkedHashMapp<K, V> extends LinkedHashMap<K, V> implements Mapp<K, V> {

	public String getString(K key) {
		return Var.toString(get(key));
	}

	public Integer getInteger(K key) {
		return Var.toInteger(get(key));
	}

	public Float getFloat(K key) {
		return Var.toFloat(get(key));
	}

	public Double getDouble(K key) {
		return Var.toDouble(get(key));
	}

	public BigDecimal getDecimal(K key) {
		return Var.toDecimal(get(key));
	}

	public Mapp putt(K key, V value) {
		put(key, value);
		return this;
	}
}
