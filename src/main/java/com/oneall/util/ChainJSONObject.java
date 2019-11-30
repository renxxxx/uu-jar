package com.oneall.util;

import com.alibaba.fastjson.JSONObject;

public class ChainJSONObject extends JSONObject {

	public ChainJSONObject(String key, Object value) {
		super.put(key, value);
	}

	public ChainJSONObject() {
	}

	public ChainJSONObject put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
