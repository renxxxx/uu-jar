package archive;

import com.alibaba.fastjson.JSONObject;

public class JsonOo extends JSONObject {

	public JsonOo(String key, Object value) {
		super.put(key, value);
	}

	public JsonOo() {
	}

	public JsonOo put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Object get(String key) {
		return super.get(key);
	}
}
