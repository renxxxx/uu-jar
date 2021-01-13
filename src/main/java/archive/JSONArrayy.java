package archive;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONArrayy extends JSONArray {

	public JSONArrayy(Object... eArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(eArr)));
	}

	public JSONArrayy() {
	}

	public JSONArrayy put(Object e) {
		super.add(e);
		return this;
	}
}
