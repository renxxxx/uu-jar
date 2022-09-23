package renx.cc.uu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Urluu {
	public static String pureName(String url) {
		if (url == null || url.isEmpty())
			return null;
		return url.replaceAll("^.*/", "");
	}

	public static String removeParams(String url) {
		if (url == null || url.isEmpty())
			return null;
		int a = url.indexOf("?");
		if (a == -1)
			return url;
		else
			return url.substring(0, a);
	}

	public static String path(String url) {
		if (url == null || url.isEmpty())
			return null;
		url = beautify(url);
		String path = url.substring(url.indexOf("/") + 1);
		path = path.substring(path.indexOf("/") + 1);
		path = path.substring(path.indexOf("/") + 1);
		return "/" + path;
	}

	// TODO
	public static String name(String url) {
		if (url == null || url.isEmpty())
			return null;
		String url1 = url.replaceAll("/+$", "");
		url1 = url1.replaceAll("^.*/", "");
		return url1;
	}

	// TODO
	public static String removeExt(String str) {
		if (str == null || str.isEmpty())
			return null;
		return str.replaceAll("\\.[^\\.]*$", "");
	}

	// TODO
	public static String ext(String str) {
		if (str == null || str.isEmpty())
			return null;
		Pattern p = Pattern.compile("^.*\\.(.*)$");
		Matcher matcher = p.matcher(name(str));
		matcher.matches();
		try {
			return matcher.group(1);
		} catch (RuntimeException e) {
			return null;
		}
	}

	public static String build(List<String> parts) {
		return build(parts.toArray(new String[] {}));
	}

	public static String build(String... parts) {
		String url = "";
		for (int i = 0; i < parts.length; i++) {
			if (StringUtils.isEmpty(parts[i]))
				continue;
			if (i == parts.length - 1)
				url = url + parts[i];
			else
				url = url + parts[i] + "/";
		}
		return beautify(url);
	}

	public static String beautify(String url) {
		if (url == null || url.isEmpty())
			return null;
		url = url.replaceAll("(\\\\+|/+)", "/");

		url = url.replaceFirst("https:/", "https://");
		url = url.replaceFirst("http:/", "http://");

		return url;
	}

	public static String makeHttp(String url) {
		if (url == null || url.isEmpty())
			return null;

		if (!url.startsWith("http")) {
			if (url.startsWith("//"))
				url = "http:" + url;
			else
				url = "http://" + url;
		}
		return url;
	}

	public static String querystring(String url) throws UnsupportedEncodingException {
		if (url == null || url.isEmpty())
			return null;
		String querystring = null;
		try {
			querystring = url.split("\\?")[1].split("#")[0];
		} catch (Exception e) {

		}
		return querystring;
	}

	public static MMap params(String querystring) throws UnsupportedEncodingException {
		return params(querystring, "utf-8");
	}

	public static MMap params(String querystr, String encode) throws UnsupportedEncodingException {
		if (querystr == null || querystr.trim().isEmpty())
			return MMap.build();
		encode = querystr == null || querystr.trim().isEmpty() ? "utf-8" : encode;
		String[] params = querystr.split("&");

		Map<String, String> parsedParams = new HashMap<String, String>(params.length);
		for (String p : params) {
			String[] kv = p.split("=");
			if (kv.length == 2) {
				parsedParams.put(kv[0], URLDecoder.decode(kv[1], encode));
			}
		}
		return MMap.build(parsedParams);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(name("https://www.douyin.com/user/werwq/wer.rr"));
	}

	@Deprecated
	public static String stripExtName(String name) {
		return name.replaceAll("\\.[^\\.]*$", "");
	}

	@Deprecated
	public static String getExtName(String name) {
		Pattern p = Pattern.compile("^.*\\.(.*)$");
		Matcher matcher = p.matcher(name);
		matcher.matches();
		try {
			return matcher.group(1);
		} catch (RuntimeException e) {
			return "";
		}
	}

	// public static Map<String, String> URLRequest(String URL) {
	// URL = URLDecoder.decode(URL);
	// Map<String, String> mapRequest = new HashMap<String, String>();
	//
	// String[] arrSplit = null;
	//
	// String[] ss = URL.split("\\?");
	// if (ss.length <= 1)
	// return mapRequest;
	// if (ss.length > 2)
	// return mapRequest;
	//
	// String strUrlParam = ss[1];
	// if (strUrlParam == null || strUrlParam.isEmpty()) {
	// return mapRequest;
	// }
	// // 每个键值为一组 www.2cto.com
	// arrSplit = strUrlParam.split("[&]");
	// for (String strSplit : arrSplit) {
	// String[] arrSplitEqual = strSplit.split("[=]");
	//
	// // 解析出键值
	// if (arrSplitEqual.length > 1) {
	// // 正确解析
	// mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
	// } else {
	// if (arrSplitEqual[0] != "") {
	// // 只有参数没有值，不加入
	// mapRequest.put(arrSplitEqual[0], "");
	// }
	// }
	// }
	// return mapRequest;
	// }
}
