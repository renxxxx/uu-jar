package archive;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonAa extends JSONArray {

	public JsonAa(Object... eArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(eArr)));
	}

	public JsonAa() {
	}

	public JsonAa put(Object e) {
		super.add(e);
		return this;
	}
}
