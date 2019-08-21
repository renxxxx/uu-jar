package com.onerunsall.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONArrayChain extends JSONArray {

	public JSONArrayChain(JSONObject... jsonObjectArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(jsonObjectArr)));
	}

	public JSONArrayChain() {
	}

	public JSONArrayChain put(JSONObject jSONObject) {
		super.add(jSONObject);
		return this;
	}
}
