package com.oneall.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONArrayChain extends JSONArray {

	public JSONArrayChain(Object... eArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(eArr)));
	}

	public JSONArrayChain() {
	}

	public JSONArrayChain put(Object e) {
		super.add(e);
		return this;
	}
}
