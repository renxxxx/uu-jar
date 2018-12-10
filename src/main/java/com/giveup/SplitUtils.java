package com.giveup;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SplitUtils {
	public static Logger logger = Logger.getLogger(SplitUtils.class);

	public static String[] toArray(String split, String regex, boolean clearEmpty) {
		if (split == null || split.trim().isEmpty())
			return new String[] {};
		else {
			String[] splitArr = split.split(regex);
			List<String> ss = new ArrayList();
			if (clearEmpty) {
				for (String splitArrEle : splitArr) {
					if (splitArrEle != null && !splitArrEle.trim().isEmpty())
						ss.add(splitArrEle);
				}
				splitArr = ss.toArray(new String[] {});
			}
			return splitArr;
		}
	}

	public static String toSplit(String[] splitArr, String regex, boolean clearEmpty) {
		if (splitArr == null || splitArr.length == 0)
			return "";
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < splitArr.length; i++) {
			if (clearEmpty && (splitArr[i] == null || splitArr[i].trim().isEmpty()))
				continue;
			s = s.append(regex).append(splitArr[i]);
		}
		String ss = s.toString();
		if (ss.length() > 0)
			ss = ss.substring(1);
		return ss;
	}
}
