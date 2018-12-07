package com.giveup;

import org.apache.log4j.Logger;

public class StrUtils {
	public static Logger logger = Logger.getLogger(StrUtils.class);

	public static boolean isNumber(String value) {
		try {
			new Double(value);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

}
