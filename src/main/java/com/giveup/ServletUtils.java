package com.giveup;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ServletUtils {
	public static Logger logger = Logger.getLogger(ServletUtils.class);

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
}
