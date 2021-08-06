package renx.uu;

import com.alibaba.fastjson.JSONObject;

public class JSONOo extends JSONObject {

	public JSONOo(String key, Object value) {
		super.put(key, value);
	}

	public JSONOo() {
	}

	public JSONOo put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Object get(String key) {
		return super.get(key);
	}
}
