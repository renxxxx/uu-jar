package com.giveup;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class StrUtils {
	public static Logger logger = Logger.getLogger(StrUtils.class);

	public static boolean isJSON(String str) {
		try {
			JSON.parseObject(str);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static boolean isNumber(String str) {
		try {
			new Double(str);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static boolean isInteger(String str) {
		try {
			new Integer(str);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out
				.println(Pattern.matches("^[!@#$%^&*()_-`=+~{}\\[\\];:'\\\",.<>?/a-zA-Z0-9]{6,20}$", "12{1``[\"`~``3"));
	}
}
