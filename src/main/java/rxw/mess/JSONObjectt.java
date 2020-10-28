package rxw.mess;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectt extends JSONObject {

	public JSONObjectt(String key, Object value) {
		super.put(key, value);
	}

	public JSONObjectt() {
	}

	public JSONObjectt put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Object get(String key) {
		return super.get(key);
	}
}
