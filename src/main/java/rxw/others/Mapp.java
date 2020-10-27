package rxw.others;

import java.math.BigDecimal;
import java.util.Map;

public interface Mapp<K, V> extends Map<K, V> {

	public String getString(K key);

	public Integer getInteger(K key);

	public Float getFloat(K key);

	public Double getDouble(K key);

	public BigDecimal getDecimal(K key);

	Mapp putt(K key, V value);
}
