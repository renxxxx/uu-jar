package com.inininininin.util;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectChain extends JSONObject {

	public JSONObjectChain(String key, Object value) {
		super.put(key, value);
	}

	public JSONObjectChain() {
	}

	public JSONObjectChain put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Object get(String key) {
		return super.get(key);
	}
}
