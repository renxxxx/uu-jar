package renx.uu;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OOkHttp {
	public OkHttpClient okHttpClient = null;
	public Builder request = null;

	public static OOkHttp build(OkHttpClient okHttpClient) {
		OOkHttp oOkHttp = new OOkHttp();
		oOkHttp.okHttpClient = okHttpClient;
		return oOkHttp;
	}

	public OOkHttp url(String url) {
		request = new Request.Builder();
		request.url(url);
		return this;
	}

	public OOkHttp post(String url) {
		request = new Request.Builder();
		request.url(url);
//				.url(url).post(body).build();;
//		Response response = App.okHttpClient.newCall(request).execute();
//		ResponseBody responseBody = response.body();
		return this;
	}
}
