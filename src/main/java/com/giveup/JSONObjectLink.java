package com.giveup;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectLink extends JSONObject {

	public JSONObjectLink(String key, Object value) {
		super.put(key, value);
	}

	public JSONObjectLink() {
	}

	public JSONObjectLink put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
