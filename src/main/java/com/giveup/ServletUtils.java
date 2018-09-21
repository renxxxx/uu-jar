package com.giveup;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ServletUtils {
	public static Logger logger = Logger.getLogger(ServletUtils.class);

	public static String getClientAddr(HttpServletRequest request) {
		String ip = null;
		ip = request.getHeader("X-Real-IP");
		if (ip != null && !ip.isEmpty())
			return ip;
		ip = request.getRemoteAddr();
		if (ip != null && !ip.isEmpty())
			return ip;
		return ip;
	}

}
