package renx.uu;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OOkHttp {
	public OkHttpClient client = null;
	public Builder requestBuilder = null;
	public MMap paramm = MMap.build();

	public static OOkHttp build(OkHttpClient client) {
		OOkHttp oOkHttp = new OOkHttp();
		oOkHttp.client = client;
		return oOkHttp;
	}

	public OOkHttp buildRequest() {
		if (requestBuilder == null) {
			requestBuilder = new Request.Builder();
		}
		return this;
	}

	public OOkHttp url(String url) {
		buildRequest();
		requestBuilder.url(url);
		return this;
	}

	public OOkHttp header(Object name, Object value) {
		buildRequest();
		requestBuilder.addHeader(name == null ? null : name.toString(), value == null ? null : value.toString());
		return this;
	}

	public OOkHttp param(Object name, Object value) {
		buildRequest();
		paramm.put(name == null ? null : name.toString(), value == null ? null : value.toString());
		return this;
	}

	public OOkHttp paramm(MMap paramm$) {
		buildRequest();
		paramm.putAll(paramm$);
		return this;
	}

	public OOkHttp paramm(Map paramm$) {
		buildRequest();
		paramm.putAll(paramm$);
		return this;
	}

	public MMap postJsonToJson() throws IOException {
		buildRequest();
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), paramm.toJSONString());
		requestBuilder.post(body);
		Request request = requestBuilder.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {

			ResponseBody responseBody = response.body();
			String result = responseBody.string();
			responseBody.close();
			result = StringUtils.trimToNull(result);
			response.close();

			return MMap.build(JSON.parseObject(result));
		}
		return MMap.build();
	}

	public MMap postFormToJson() throws IOException {
		buildRequest();
		FormBody.Builder builder = new FormBody.Builder();

		for (int i = 0; i < paramm.keySet().size(); i++) {
			Var name = Var.build(paramm.keySet().iterator().next());
			if (name.isEmpty())
				continue;
			Var value = paramm.get(name);
			builder.add(name.toString(), value.value);
		}

		RequestBody body = builder.build();

		requestBuilder.post(body);
		Request request = requestBuilder.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {

			ResponseBody responseBody = response.body();
			String result = responseBody.string();
			responseBody.close();
			result = StringUtils.trimToNull(result);
			response.close();

			return MMap.build(JSON.parseObject(result));
		}
		return MMap.build();
	}

//	public OOkHttp postJson(String url, MMap headerm, MMap paramm) {
////		RequestBody body = new FormBody.Builder().add("weaid", "1").add("date", "2018-08-13").add("appkey", "10003")
////				.add("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4").add("format", "json").build();
//		headerm = MMap.build(headerm);
//		paramm = MMap.build(paramm);
//
//		RequestBody body = RequestBody.create(MediaType.parse("application/json"), paramm.toJSONString());
//
//		Request.Builder requestBuilder = new okhttp3.Request.Builder();
//		requestBuilder.url(url).post(body);
//
//		if (headerm.isExisting()) {
//			for (int i = 0; i < headerm.keySet().size(); i++) {
//				Var name = Var.build(headerm.keySet().iterator().next());
//				if (name.isEmpty())
//					continue;
//				Var value = headerm.get(name);
//				requestBuilder.addHeader(name.toString(), value.value);
//			}
//		}
//		okhttp3.Request request = requestBuilder.build();
//
//		OkHttpClient okHttpClient = new OkHttpClient();
//		Response response = okHttpClient.newCall(request).execute();
//		if (response.isSuccessful()) {
//			return response.body().string();
//		} else {
//			throw new IOException("Unexpected code " + response);
//		}
//		return this;
//	}
}
