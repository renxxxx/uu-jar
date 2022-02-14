package renx.uu;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MMap {

	public Map map = null;

	public Set keySet() {
		if (this.map == null)
			return null;
		else {
			return this.map.keySet();
		}
	}

	public static MMap build(Map map) {
		if (map == null)
			return new MMap();
		else {
			MMap mapp = new MMap();
			mapp.map = map;
			return mapp;
		}
	}

	public static MMap build() {
		return build(null);
	}

	public MMap put(Object key, Object value, boolean if_) {
		if (if_) {
			put(key, value);
		}
		return this;
	}

	public MMap put(Object key, Object value) {
		if (this.map == null)
			this.map = new LinkedHashMap();
		if (value instanceof MMap)
			value = ((MMap) value).map;
		if (value instanceof LList)
			value = ((LList) value).list;
		if (value instanceof Var)
			value = ((Var) value).value;
		this.map.put(key, value);
		return this;
	}

	public MMap putAll(Map map) {
		if (this.map == null)
			this.map = new LinkedHashMap();
		if (map != null) {
			Iterator keys = map.keySet().iterator();
			while (keys.hasNext()) {
				Object key = keys.next();
				this.put(key, map.get(key));
			}
		}
		return this;
	}

	public MMap putAll(String keyPrefix, Map map) {
		if (this.map == null)
			this.map = new LinkedHashMap();
		if (map == null)
			return this;

		Iterator keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			String key2 = keyPrefix + (key.toString().charAt(0) + "").toUpperCase() + key.toString().substring(1);
			this.map.put(key2, map.get(key));
		}
		return this;
	}

	public MMap putAll(MMap mmap) {
		if (mmap == null)
			return this;
		return putAll(mmap.map);
	}

	public MMap putAll(String keyPrefix, MMap mmap) {
		if (mmap == null)
			return this;
		return putAll(keyPrefix, mmap.map);
	}

	public Object get(Object key) {
		if (this.map == null)
			return null;
		return map.get(key);
	}

	public Object remove(Object key) {
		if (this.map == null)
			return null;
		return map.remove(key);
	}

	public String getString(Object key) {
		if (map == null)
			return null;
		return Var.toString(map.get(key));
	}

	public Boolean getBoolean(Object key) {
		if (map == null)
			return null;
		return Var.toBoolean(map.get(key));
	}

	public Integer getInteger(Object key) {
		if (map == null)
			return null;
		return Var.toInteger(map.get(key));
	}

	public Long getLong(Object key) {
		if (map == null)
			return null;
		return Var.toLong(map.get(key));
	}

	public BigDecimal getDecimal(Object key) {
		if (map == null)
			return null;
		return Var.toDecimal(map.get(key));
	}

	public Float getFloat(Object key) {
		if (map == null)
			return null;
		return Var.toFloat(map.get(key));
	}

	public Date getDate(Object key) {
		if (map == null)
			return null;
		return Var.toDate(map.get(key));
	}

	public LList getList(Object key) {
		if (map == null)
			return LList.build();
		return LList.build((List) map.get(key));
	}

	public MMap getMap(Object key) {
		if (map == null)
			return MMap.build();
		return MMap.build((Map) map.get(key));
	}

	public boolean isEmpty() {
		if (this.map == null)
			return true;
		else
			return this.map.isEmpty();
	}

	public boolean isExist(String key) {
		return notEmpty(key);
	}

	public boolean notEmpty(String key) {
		Object value = this.get(key);
		if (value == null)
			return false;
		if (value.toString().isEmpty())
			return false;
		return true;
	}

	public boolean isEmpty(String key) {
		return !notEmpty(key);
	}

	public boolean isExist() {
		return notEmpty();
	}

	public boolean notEmpty() {
		return !isEmpty();
	}

	public boolean getAndNotEquals(Object key, Object target) {
		return !getAndEquals(key, target);
	}

	public boolean getAndEquals(Object key, Object target) {
		if (map == null)
			return false;
		Object value = map.get(key);
		if (value == target)
			return true;
		return value.toString().equals(target.toString());
	}

	@Override
	public String toString() {
		return this.map == null ? null : this.map.toString();
	}

	public String toJSONString() {
		return JSON.toJSONString(this.map);
	}

	public String toJSONString(SerializerFeature... features) {
		return JSON.toJSONString(this.map, features);
	}
}
