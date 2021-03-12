package renx.archive;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONAa extends JSONArray {

	public JSONAa(Object... eArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(eArr)));
	}

	public JSONAa() {
	}

	public JSONAa put(Object e) {
		super.add(e);
		return this;
	}
}
