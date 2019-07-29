package com.giveup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class ListUtils {
	public static Logger logger = Logger.getLogger(ListUtils.class);

	public static <T> List<T> extractOffEles(T[] oldArr, T[] newArr) {
		if (oldArr == null || newArr == null)
			return null;
		List<T> oldList = new ArrayList<T>(Arrays.asList(oldArr));
		List<T> newList = new ArrayList<T>(Arrays.asList(newArr));
		return extractOffEles(oldList, newList);
	}

	public static <T> List<T> extractOffEles(List<T> oldList, List<T> newList) {
		if (oldList == null || newList == null)
			return null;

		if (oldList.size() == 0)
			return oldList;
		if (newList.size() == 0)
			return oldList;

		List<T> offEles = new ArrayList<T>();
		for (T oldEle : oldList) {
			if (!newList.contains(oldEle))
				offEles.add(oldEle);
		}
		return offEles;
	}

	public static void main(String[] args) {
		List<String> aa = new ArrayList<String>();
		aa.add("a1");
		aa.add("a2");
		aa.add("a3");
		aa.add("a4");

		List<String> bb = new ArrayList<String>();
		bb.add("a1");
		bb.add("a2");
		bb.add("a3");
		bb.add("a4");
		System.out.println(extractOffEles(aa, bb));
	}
}
