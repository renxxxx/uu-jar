package com.giveup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	private static CacheMap<String, Pattern> cache = new CacheMap<String, Pattern>();

	public static boolean matches(String regex, CharSequence input) {
		Pattern pattern = cache.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
			cache.put(regex, pattern, 1 * 60 * 60l);
		}
		Matcher m = pattern.matcher(input);
		return m.matches();
	}
}
