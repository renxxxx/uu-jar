package com.giveup;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectWrap extends JSONObject {

	public JSONObjectWrap(String key, Object value) {
		super.put(key, value);
	}

	public JSONObjectWrap() {
	}

	public JSONObjectWrap put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
