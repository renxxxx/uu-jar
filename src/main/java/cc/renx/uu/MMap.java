package cc.renx.uu;

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

	public Map map = new LinkedHashMap();

	public Set keySet() {
		if (this.map == null)
			return null;
		else {
			return this.map.keySet();
		}
	}

//	public static MMap build(MMap mmap) {
//		if (mmap == null)
//			return new MMap();
//		else {
//			MMap mapp = new MMap();
//			mapp.map = mmap.map;
//			return mapp;
//		}
//	}
//
//	public static MMap build(Map map) {
//		if (map == null)
//			return new MMap();
//		else {
//			MMap mapp = new MMap();
//			mapp.map = map;
//			return mapp;
//		}
//	}

	public static MMap build(Object obj) {
		MMap mmap = new MMap();
		if (obj == null)
			mmap = mmap;
		if (obj instanceof MMap) {
			mmap.map = ((MMap) obj).map;
		}
		if (obj instanceof Map) {
			mmap.map = (Map) obj;
		}
		try {
			mmap.map = JSON.parseObject(obj.toString(), LinkedHashMap.class);
		} catch (Exception e) {
		}
		return mmap;
	}

	public static MMap build() {
		return new MMap();
	}

	public static MMap build(Object key, Object value) {
		return build().put(key, value);
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

		if (key instanceof Var)
			key = ((Var) key).value;
		this.map.put(key, value);
		return this;
	}

	public MMap putAll(Map map) {
		if (map == null)
			return this;
		Iterator keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			put(key, map.get(key));
		}
		return this;
	}

	public MMap putAll(MMap mmap) {
		if (mmap == null)
			return this;
		return putAll(mmap.map);
	}

	public MMap putAll(String keyPrefix, Map map) {
		if (map == null)
			return this;

		Iterator keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			String key2 = keyPrefix + (key.toString().charAt(0) + "").toUpperCase() + key.toString().substring(1);
			put(key2, map.get(key));
		}
		return this;
	}

	public MMap putAll(String keyPrefix, MMap mmap) {
		if (mmap == null)
			return this;
		return putAll(keyPrefix, mmap.map);
	}

	public Object getObject(Object key) {
		if (this.map == null)
			return null;
		if (key instanceof Var)
			key = ((Var) key).value;
		return map.get(key);
	}

	public Object remove(Object key) {
		if (this.map == null)
			return null;
		if (key instanceof Var)
			key = ((Var) key).value;
		return map.remove(key);
	}

	public Var get(Object key) {
		return Var.build(getObject(key));
	}

	public String getString(Object key) {
		return Var.toString(getObject(key));
	}

	public Boolean getBoolean(Object key) {
		return Var.toBoolean(getObject(key));
	}

	public Integer getInteger(Object key) {
		return Var.toInteger(getObject(key));
	}

	public Long getLong(Object key) {
		return Var.toLong(getObject(key));
	}

	public BigDecimal getDecimal(Object key) {
		return Var.toDecimal(getObject(key));
	}

	public Float getFloat(Object key) {
		return Var.toFloat(getObject(key));
	}

	public Date getDate(Object key) {
		return Var.toDate(getObject(key));
	}

	public LList getList(Object key) {
		return Var.toList(getObject(key));
	}

	public MMap getMap(Object key) {
		return Var.toMap(getObject(key));
	}

	public boolean isEmpty() {
		if (this.map == null)
			return true;
		else
			return this.map.isEmpty();
	}

	public boolean isExisting(String key) {
		return notEmpty(key);
	}

	public boolean notEmpty(String key) {
		Object value = getObject(key);
		if (value == null)
			return false;
		if (value instanceof Var)
			return ((Var) value).notEmpty();
		if (value instanceof MMap)
			return ((MMap) value).notEmpty();
		if (value instanceof LList)
			return ((LList) value).notEmpty();
		if (value.toString() == null)
			return false;
		if (value.toString().isEmpty())
			return false;
		return true;
	}

	public boolean isEmpty(String key) {
		return !notEmpty(key);
	}

	public boolean isExisting() {
		return notEmpty();
	}

	public boolean notEmpty() {
		return !isEmpty();
	}

	public boolean getAndNotEquals(Object key, Object target) {
		return !getAndEquals(key, target);
	}

	public boolean getAndEquals(Object key, Object target) {
		Object value = getObject(key);

		if (value == target)
			return true;
		if (value == null || target == null)
			return false;

		String vs = value.toString();
		String ts = target.toString();
		if (vs == ts)
			return true;
		if (vs == null || ts == null)
			return false;
		return vs.equals(ts);
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

	public String toJSONString2() {
		return JSON.toJSONString(this.map, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
	}

	public LList toList() {
		LList llist = LList.build();
		Iterator keys = keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			llist.add(getObject(key));
		}
		return llist;
	}

	public Var attrChain(Object chainKeys) throws Exception {
		return Var.attrChain(this, chainKeys);
	}

	public Var attr(Object... keys) throws Exception {
		return Var.attr(this, keys);
	}

	public Object attrObjectChain(Object chainKeys) throws Exception {
		return Var.attrObjectChain(this, chainKeys);
	}

	public Object attrObject(Object... keys) throws Exception {
		return Var.attrObject(this, keys);
	}
}
