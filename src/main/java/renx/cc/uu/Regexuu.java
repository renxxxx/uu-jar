package renx.cc.uu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Regexuu {
	private static Logger logger = LoggerFactory.getLogger(Regexuu.class);

	public static boolean find(String source, String regex) {
		if (source == null || source.isEmpty() || regex == null || regex.isEmpty())
			return false;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		return matcher.find();
	}

	public static String group(String source, String regex) {
		return group(source, regex, 1);
	}

	public static String group(String source, String regex, int group) {
		if (source == null || source.isEmpty() || regex == null || regex.isEmpty())
			return null;
		String target = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) {
			int groupCount = matcher.groupCount();
			if (group <= groupCount) {
				target = matcher.group(group);
			}
		}
		return target;
	}

	public static void main(String[] args) {
		System.out.println("/123/123.12".replaceAll("(^/)|(/$)", ""));
		// Pattern p = Pattern.compile("^/?(.*?)/.*$");
		// Matcher m = p.matcher("zhongan/oss/aa.txt");
		// m.matches();
		// System.out.println(m.group(1));
	}
}
