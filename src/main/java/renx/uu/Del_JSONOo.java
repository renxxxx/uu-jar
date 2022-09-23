package renx.uu;

import com.alibaba.fastjson.JSONObject;

public class Del_JSONOo extends JSONObject {

	public Del_JSONOo(String key, Object value) {
		super.put(key, value);
	}

	public Del_JSONOo() {
	}

	public Del_JSONOo put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Object get(String key) {
		return super.get(key);
	}
}
