package com.giveup;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class OtherUtils {
	public static Logger logger = Logger.getLogger(OtherUtils.class);

	public static List<String> extractOffStrs(List<String> oldStrs, List<String> newStrs, boolean force) {
		if (oldStrs == null || oldStrs.isEmpty())
			return new ArrayList();
		if (newStrs == null || newStrs.isEmpty()) {
			if (force)
				return oldStrs;
			else
				return new ArrayList();
		}
		List<String> offUrls = new ArrayList<String>();
		for (String oldUrl : oldStrs) {
			if (!newStrs.contains(oldUrl))
				offUrls.add(oldUrl);
		}
		return offUrls;
	}

	public static List<String> extractOffStrs(String[] oldStrs, String[] newStrs, boolean force) {
		if (oldStrs == null || oldStrs.length == 0)
			return new ArrayList();
		if (newStrs == null || newStrs.length == 0) {
			if (force)
				return new ArrayList(Arrays.asList(oldStrs));
			else
				return new ArrayList();
		}
		return extractOffStrs(new ArrayList(Arrays.asList(oldStrs)), new ArrayList(Arrays.asList(newStrs)), force);
	}

	public static boolean validUrlIs404(String url) {
		return true;
	}

}
