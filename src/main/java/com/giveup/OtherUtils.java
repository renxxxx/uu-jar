package com.giveup;

import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class OtherUtils {
	public static Logger logger = Logger.getLogger(OtherUtils.class);

	public static String buildUrlPath(String... paths) {
		String urlPath = "";
		for (String path : paths) {
			urlPath = urlPath + "/" + path;
		}
		return twistingPathSeparator(urlPath);
	}

	public static String twistingPathSeparator(String path) {
		path = path.replaceAll("\\\\+", "/");
		path = path.replaceAll("/+", "/");
		return path;
	}

	public static void main(String[] args) {
		System.out.println(twistingArrayString(",,,,123,,1234,,", ","));
	}

	public static String twistingArrayString(String str, String separator) {
		String str1 = "";
		StringBuilder sb2 = new StringBuilder();
		if (str == null)
			return null;
		str1 = str.trim();
		if (str1.isEmpty())
			return "";
		String[] str1s = str1.split(separator);
		for (String temp : str1s) {
			temp = temp.trim();
			if (!temp.isEmpty())
				sb2 = sb2.append(separator).append(temp);
		}
		String str2 = sb2.toString();
		if (!str2.isEmpty())
			return str2.substring(1);
		else
			return str2;
	}

	public static String fileExt(String fileName) {
		String[] parts = fileName.split("\\.");
		if (parts.length == 1)
			return null;

		return parts[parts.length - 1];
	}

	public static boolean equalsWithAny(CharSequence cs, CharSequence... css) {
		for (CharSequence c : css) {
			if (cs == c || cs.equals(c))
				return true;
		}
		return false;
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
