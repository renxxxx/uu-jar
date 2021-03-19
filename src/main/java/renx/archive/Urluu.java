package renx.archive;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Urluu {

	// TODO
	public static String getName(String url) {
		if (url == null || url.isEmpty())
			return null;
		return url.replaceAll("^.*/", "");
	}

	// TODO
	public static String removeExt(String str) {
		if (str == null || str.isEmpty())
			return null;
		return str.replaceAll("\\.[^\\.]*$", "");
	}

	// TODO
	public static String getExt(String str) {
		if (str == null || str.isEmpty())
			return null;
		Pattern p = Pattern.compile("^.*\\.(.*)$");
		Matcher matcher = p.matcher(getName(str));
		matcher.matches();
		try {
			return matcher.group(1);
		} catch (RuntimeException e) {
			return null;
		}
	}

	public static String buildPath(List<String> pathParts) {
		return buildPath(pathParts.toArray(new String[] {}));
	}

	public static String buildPath(String... pathParts) {
		String url = "";
		for (int i = 0; i < pathParts.length; i++) {
			if (StringUtils.isEmpty(pathParts[i]))
				continue;
			if (i == pathParts.length - 1)
				url = url + pathParts[i];
			else
				url = url + pathParts[i] + "/";
		}
		return prettyUrl(url);
	}

	public static String prettyUrl(String path) {
		path = path.replaceAll("(\\\\+|/+)", "/");
		return path;
	}

	public static Map<String, String> parseQueryString(String queryString, String encode)
			throws UnsupportedEncodingException {
		if (queryString == null || queryString.trim().isEmpty())
			return null;
		String[] params = queryString.split("&");

		Map<String, String> parsedParams = new HashMap<String, String>(params.length);
		for (String p : params) {
			String[] kv = p.split("=");
			if (kv.length == 2) {
				parsedParams.put(kv[0], URLDecoder.decode(kv[1], encode));
			}
		}
		return parsedParams;
	}

	public static void main(String[] args) {
		System.out.println(removeExt("sadf.er"));
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
