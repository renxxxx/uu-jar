package renx.cc.uu;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Del_JSONAa extends JSONArray {

	public Del_JSONAa(Object... eArr) {
		super.addAll(new ArrayList<Object>(Arrays.asList(eArr)));
	}

	public Del_JSONAa() {
	}

	public Del_JSONAa put(Object e) {
		super.add(e);
		return this;
	}
}
