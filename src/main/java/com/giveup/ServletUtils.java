package com.giveup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class ServletUtils {
	public static Logger logger = Logger.getLogger(ServletUtils.class);

	public static String getParameter(HttpServletRequest request, String name) {
		Map<String, String> parameterMap = (Map) request.getAttribute("parameterMap");
		if (parameterMap == null) {
			parameterMap = new HashMap();
			request.setAttribute("parameterMap", parameterMap);
		}
		if (parameterMap.containsKey(name))
			return parameterMap.get(name);

		return request.getParameter(name);
	}

	public static void setParameter(HttpServletRequest request, String name, String value) {
		Map<String, String> parameterMap = (Map) request.getAttribute("parameterMap");
		if (parameterMap == null) {
			parameterMap = new HashMap();
		}
		parameterMap.put(name, value);
		request.setAttribute("parameterMap", parameterMap);
	}

	public static String getClientAddr(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("X-Real-IP");
		if (value != null && !value.isEmpty())
			return value;
		value = request.getRemoteAddr();
		if (value != null && !value.isEmpty())
			return value;
		return value;
	}

	public static String getCookie(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key))
				return cookie.getValue();
		}
		return null;
	}

	public static String getClientDoScheme(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("X-Forwarded-Scheme");
		if (value != null && !value.isEmpty())
			return value;
		value = request.getScheme();
		if (value != null && !value.isEmpty())
			return value;
		return value;
	}

	public static String getClientDoHost(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("remote-host");
		if (value != null && !value.isEmpty())
			return value;
		value = request.getHeader("HOST");
		if (value != null && !value.isEmpty())
			return value;
		return value;
	}

	public static void logParameters(HttpServletRequest request) {
		logger.info("parameters");
		Map<String, String[]> map = request.getParameterMap();
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			logger.info(key + ":" + Arrays.toString(map.get(key)));
		}
	}

}
