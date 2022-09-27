package cc.renx.uu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Servletuu {
	private static Logger logger = LoggerFactory.getLogger(Servletuu.class);

	public static Map<String, List<String>> headerm(HttpServletResponse response) {
		Map<String, List<String>> headersMap = new LinkedHashMap<String, List<String>>();
		Collection<String> names = response.getHeaderNames();
		for (Iterator iterator = names.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			Collection<String> ve = response.getHeaders(name);

			List<String> valueList = new ArrayList<String>();
			for (int i = 0; i < valueList.size(); i++) {
				valueList.add(valueList.get(i));
			}
			headersMap.put(name, valueList);
		}
		return headersMap;
	}

	public static Map<String, List<String>> headerm(HttpServletRequest request) {
		Map<String, List<String>> headersMap = (Map<String, List<String>>) request.getAttribute("headerm-10000");
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

	public static Map<String, Cookie> cookiem(HttpServletRequest request) {
		Map<String, Cookie> cookiesMap = (Map<String, Cookie>) request.getAttribute("cookiem-10000");
		if (cookiesMap == null)
			cookiesMap = new LinkedHashMap<String, Cookie>();
		else {
			return cookiesMap;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
			return cookiesMap;
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			cookiesMap.put(cookie.getName(), cookie);
		}
		request.setAttribute("cookiesMap-10000", cookiesMap);
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

	public static String ip(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("X-Real-IP");
		if (value != null && !value.isEmpty())
			return value;
		value = request.getRemoteAddr();
		if (value != null && !value.isEmpty())
			return value;
		return value;
	}

	public static CCookie cookie(String key, HttpServletRequest request) {
		Map<String, Cookie> cookiem = cookiem(request);
		CCookie cookie = CCookie.build(cookiem.get(key));
		return cookie;
	}

	public static String scheme(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("remote-scheme");
		if (value != null && !value.isEmpty())
			return value;

		value = request.getScheme();
		if (value != null && !value.isEmpty())
			return value;

		return value;
	}

	public static String host(HttpServletRequest request) {
		String value = null;
		value = request.getHeader("remote-host");
		if (value != null && !value.isEmpty())
			return value;

		value = request.getHeader("HOST");
		if (value != null && !value.isEmpty())
			return value;

		value = request.getRemoteHost();
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
