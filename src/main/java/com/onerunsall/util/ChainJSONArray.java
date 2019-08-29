package com.onerunsall.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ChainJSONArray extends JSONArray {

	public ChainJSONArray(JSONObject... jsonObjectArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(jsonObjectArr)));
	}

	public ChainJSONArray() {
	}

	public ChainJSONArray put(JSONObject jSONObject) {
		super.add(jSONObject);
		return this;
	}
}
