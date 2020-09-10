package inininininin.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class ServletUtils {
	public static Logger logger = Logger.getLogger(ServletUtils.class);

	public static Map<String, List<String>> headersMap(HttpServletRequest request) {
		Map<String, List<String>> headersMap = (Map<String, List<String>>) request.getAttribute("headersMap-111111");
		if (headersMap == null)
			headersMap = new LinkedHashMap<String, List<String>>();
		else {
			return headersMap;
		}
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Enumeration<String> ve = request.getHeaders(name);

			List<String> valueList = new ArrayList<String>();
			while (ve.hasMoreElements()) {
				valueList.add(ve.nextElement());
			}
			headersMap.put(name, valueList);
		}
		return headersMap;
	}

	public static Map<String, Cookie> cookiesMap(HttpServletRequest request) {
		Map<String, Cookie> cookiesMap = (Map<String, Cookie>) request.getAttribute("cookiesMap-111111");
		if (cookiesMap == null)
			cookiesMap = new LinkedHashMap<String, Cookie>();
		else {
			return cookiesMap;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
			return cookiesMap;
		for (int i = 0; i < cookies.length; i++) {
			JSONObject hj = new JSONObject(true);
			Cookie cookie = cookies[i];
			cookiesMap.put(cookie.getName(), cookie);
		}
		request.setAttribute("cookiesMap-111111", cookiesMap);
		return cookiesMap;
	}

//	public static String getParameter(HttpServletRequest request, String name) {
//		Map<String, String> parameterMap = (Map) request.getAttribute("parameterMap");
//		if (parameterMap == null) {
//			parameterMap = new HashMap();
//			request.setAttribute("parameterMap", parameterMap);
//		}
//		if (parameterMap.containsKey(name))
//			return parameterMap.get(name);
//
//		return request.getParameter(name);
//	}
//
//	public static void setParameter(HttpServletRequest request, String name, String value) {
//		Map<String, String> parameterMap = (Map) request.getAttribute("parameterMap");
//		if (parameterMap == null) {
//			parameterMap = new HashMap();
//		}
//		parameterMap.put(name, value);
//		request.setAttribute("parameterMap", parameterMap);
//	}

	public static String getClientIp(HttpServletRequest request) {
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
		Map<String, Cookie> cookiesMap = cookiesMap(request);
		Cookie cookie = cookiesMap.get(key);
		String value = cookie == null ? null : cookie.getValue();
		return value;
	}

	public static String getOriginalScheme(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("X-Forwarded-Scheme");
		if (value != null && !value.isEmpty())
			return value;
		value = request.getScheme();
		if (value != null && !value.isEmpty())
			return value;
		return value;
	}

	public static String getOriginalHost(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("remote-host");
		if (value != null && !value.isEmpty())
			return value;
		value = request.getHeader("HOST");
		if (value != null && !value.isEmpty())
			return value;
		return value;
	}

//	public static void logParameters(HttpServletRequest request) {
//		logger.debug("parameters");
//		Map<String, String[]> map = request.getParameterMap();
//		Iterator<String> itr = map.keySet().iterator();
//		while (itr.hasNext()) {
//			String key = itr.next();
//			logger.debug(key + ":" + Arrays.toString(map.get(key)));
//		}
//	}

}
