package com.giveup;

import org.apache.log4j.Logger;

public class SplitUtils {
	public static Logger logger = Logger.getLogger(SplitUtils.class);

	public static String[] toArray(String split, String regex) {
		if (split == null || split.trim().isEmpty())
			return new String[] {};
		else {
			String[] splitArr = split.split(regex);
			return splitArr;
		}
	}

	public static String toSplit(String[] splitArr, String regex) {
		if (splitArr == null || splitArr.length == 0)
			return "";
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < splitArr.length; i++) {
			s = s.append(regex).append(splitArr[i] == null ? "" : splitArr[i]);
		}
		String ss = s.toString();
		if (ss.length() > 0)
			ss = ss.substring(1);
		return ss;
	}
}
