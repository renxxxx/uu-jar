package com.giveup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static boolean isInteger(String value) {
		try {
			new Integer(value);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(Pattern.matches("^[!@#$%^&*()_-`=+~{}\\[\\];:'\\\",.<>?/a-zA-Z0-9]{6,20}$", "12{1``[\"`~``3"));
	}
}
