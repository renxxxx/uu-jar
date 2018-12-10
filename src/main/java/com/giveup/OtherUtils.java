package com.giveup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class OtherUtils {
	public static Logger logger = Logger.getLogger(OtherUtils.class);

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

	public static List<String> extractOffStrs(List<String> oldStrs, List<String> newStrs, boolean force) {
		if (oldStrs == null || oldStrs.isEmpty())
			return new ArrayList();
		if (newStrs == null || newStrs.isEmpty()) {
			if (force)
				return oldStrs;
			else
				return new ArrayList();
		}
		List<String> offUrls = new ArrayList<String>();
		for (String oldUrl : oldStrs) {
			if (!newStrs.contains(oldUrl))
				offUrls.add(oldUrl);
		}
		return offUrls;
	}

	public static List<String> extractOffStrs(String[] oldStrs, String[] newStrs, boolean force) {
		if (oldStrs == null || oldStrs.length == 0)
			return new ArrayList();
		if (newStrs == null || newStrs.length == 0) {
			if (force)
				return new ArrayList(Arrays.asList(oldStrs));
			else
				return new ArrayList();
		}
		return extractOffStrs(new ArrayList(Arrays.asList(oldStrs)), new ArrayList(Arrays.asList(newStrs)), force);
	}

	public static String buildUrlPath(String... paths) {
		String urlPath = "";
		for (String path : paths) {
			urlPath = urlPath + "/" + path;
		}
		return twistingPathSeparator(urlPath);
	}

	public static String buildPath(String... pathParts) {
		String urlPath = "";
		for (int i = 0; i < pathParts.length; i++) {
			if (i == pathParts.length - 1)
				urlPath = urlPath + pathParts[i];
			else
				urlPath = urlPath + pathParts[i] + "/";
		}
		return twistingPathSeparator(urlPath);
	}

	public static String extractFileNamePrefix(String fileName) {
		String[] fileNameParts = fileName.split("\\.");
		StringBuilder prefixSb = new StringBuilder("");
		int toIndex = fileNameParts.length;
		if (fileNameParts.length > 1)
			toIndex--;

		for (int i = 0; i < toIndex; i++) {
			prefixSb = prefixSb.append(".").append(fileNameParts[i]);
		}
		String prefix = prefixSb.toString();
		if (prefix.length() > 1)
			prefix = prefix.substring(1);
		return prefix;
	}

	public static String twistingPathSeparator(String path) {
		path = path.replaceAll("\\\\+", "/");
		path = path.replaceAll("/+", "/");
		return path;
	}

	public static boolean urlWithSchemeIf(String url) {
		if (StringUtils.left(url, 7).equalsIgnoreCase("http://")
				|| StringUtils.left(url, 8).equalsIgnoreCase("https://"))
			return true;
		return false;
	}

	// public static String jointElesWithSeperator(List<String> arr, String
	// separator) {
	// if (arr == null || arr.size() == 0)
	// return "";
	// StringBuilder s = new StringBuilder();
	// for (int i = 0; i < arr.size(); i++) {
	// s = s.append(separator).append(arr.get(i) == null ||
	// arr.get(i).trim().isEmpty() ? "" : arr.get(i));
	// }
	// String ss = s.toString();
	// if (ss.length() > 0)
	// ss = ss.substring(1);
	// return ss;
	// }
	//
	// public static void main(String[] args) {
	// System.out.println(extractFileNamePrefix("123123123213.jpg"));
	// }
	//
	// public static String rebuildSplitString(String str, String separator) {
	// String[] ss = str.split(separator);
	// List lll = new ArrayList();
	// for (String s : ss) {
	// if (s == null)
	// continue;
	// s = s.trim();
	// if (!s.isEmpty())
	// lll.add(s);
	// }
	// return jointElesWithSeperator(lll, separator);
	// }

	// public static String twistingArrayString(String str, String separator) {
	// String str1 = "";
	// StringBuilder sb2 = new StringBuilder();
	// if (str == null)
	// return null;
	// str1 = str.trim();
	// if (str1.isEmpty())
	// return "";
	// String[] str1s = str1.split(separator);
	// for (String temp : str1s) {
	// temp = temp.trim();
	// if (!temp.isEmpty())
	// sb2 = sb2.append(separator).append(temp);
	// }
	// String str2 = sb2.toString();
	// if (!str2.isEmpty())
	// return str2.substring(1);
	// else
	// return str2;
	// }

	public static String fileExt(String fileName) {
		String[] parts = fileName.split("\\.");
		if (parts.length == 1)
			return null;

		return parts[parts.length - 1];
	}

	public static boolean equalsWithAny(CharSequence cs, CharSequence... css) {
		if (css == null)
			return false;
		for (CharSequence c : css) {
			if (cs == c)
				return true;
			if (cs != null && cs.equals(c))
				return true;
			if (c != null && c.equals(cs))
				return true;
		}
		return false;
	}

	public static boolean equalsIgnoreCaseWithAny(String str, String... strs) {
		if (strs == null)
			return false;
		for (String s : strs) {
			if (str == s)
				return true;
			if (str != null && str.equalsIgnoreCase(s))
				return true;
			if (s != null && s.equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

	public static boolean allEqualsIgnoreCaseWith(String target, String... items) {
		for (String c : items) {
			if (target == c)
				continue;
			if (target != c || !target.equalsIgnoreCase(c))
				return false;
		}
		return true;
	}

	public static boolean validUrlIs404(String url) {
		return true;
	}

	public static boolean allEqualWith(CharSequence target, CharSequence... items) {
		for (CharSequence c : items) {
			if (target == c)
				continue;
			if (target != c || !target.equals(c))
				return false;
		}
		return true;
	}

	public static Map<String, String> URLRequest(String URL) {
		URL = URLDecoder.decode(URL);
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String[] ss = URL.split("\\?");
		if (ss.length <= 1)
			return mapRequest;
		if (ss.length > 2)
			return mapRequest;

		String strUrlParam = ss[1];
		if (strUrlParam == null || strUrlParam.isEmpty()) {
			return mapRequest;
		}
		// 每个键值为一组 www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
