package com.giveup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class UrlUtils {

	public static String buildPath(boolean head, boolean end, String... pathParts) {
		String url = head ? "/" : "";
		for (int i = 0; i < pathParts.length; i++) {
			if (i == pathParts.length - 1)
				url = url + pathParts[i];
			else
				url = url + pathParts[i] + "/";
		}
		url = url + (end ? "/" : "");
		return twistingPathSeparator(url);
	}

	public static String buildPath(String... pathParts) {
		String url = "";
		for (int i = 0; i < pathParts.length; i++) {
			if (i == pathParts.length - 1)
				url = url + pathParts[i];
			else
				url = url + pathParts[i] + "/";
		}
		return twistingPathSeparator(url);
	}

	public static String twistingPathSeparator(String path) {
		path = path.replaceAll("(\\\\+|/+)", "/");
		return path;
	}

	public static Map<String, String> parseQueryStr(String queryStr, String encode)
			throws UnsupportedEncodingException {
		if (queryStr == null || queryStr.trim().isEmpty())
			return null;
		String[] params = queryStr.split("&");

		Map<String, String> parsedParams = new HashMap<String, String>(params.length);
		for (String p : params) {
			String[] kv = p.split("=");
			if (kv.length == 2) {
				parsedParams.put(kv[0], URLDecoder.decode(kv[1], encode));
			}
		}
		return parsedParams;
	}

//	public static Map<String, String> URLRequest(String URL) {
//		URL = URLDecoder.decode(URL);
//		Map<String, String> mapRequest = new HashMap<String, String>();
//
//		String[] arrSplit = null;
//
//		String[] ss = URL.split("\\?");
//		if (ss.length <= 1)
//			return mapRequest;
//		if (ss.length > 2)
//			return mapRequest;
//
//		String strUrlParam = ss[1];
//		if (strUrlParam == null || strUrlParam.isEmpty()) {
//			return mapRequest;
//		}
//		// 每个键值为一组 www.2cto.com
//		arrSplit = strUrlParam.split("[&]");
//		for (String strSplit : arrSplit) {
//			String[] arrSplitEqual = strSplit.split("[=]");
//
//			// 解析出键值
//			if (arrSplitEqual.length > 1) {
//				// 正确解析
//				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
//			} else {
//				if (arrSplitEqual[0] != "") {
//					// 只有参数没有值，不加入
//					mapRequest.put(arrSplitEqual[0], "");
//				}
//			}
//		}
//		return mapRequest;
//	}
}
