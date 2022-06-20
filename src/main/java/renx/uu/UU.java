package renx.uu;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UU {
	private static Logger logger = LoggerFactory.getLogger(UU.class);
	public static String version = "2.6.0";

	public static boolean isLinux() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("linux") > -1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWindows() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("windows") > -1) {
			return true;
		} else {
			return false;
		}
	}

//	public static List<String> extractOffStrs(List<String> oldStrs, List<String> newStrs, boolean force) {
//		if (oldStrs == null || oldStrs.isEmpty())
//			return new ArrayList();
//		if (newStrs == null || newStrs.isEmpty()) {
//			if (force)
//				return oldStrs;
//			else
//				return new ArrayList();
//		}
//		List<String> offUrls = new ArrayList<String>();
//		for (String oldUrl : oldStrs) {
//			if (!newStrs.contains(oldUrl))
//				offUrls.add(oldUrl);
//		}
//		return offUrls;
//	}
//
//	public static List<String> extractOffStrs(String[] oldStrs, String[] newStrs, boolean force) {
//		if (oldStrs == null || oldStrs.length == 0)
//			return new ArrayList();
//		if (newStrs == null || newStrs.length == 0) {
//			if (force)
//				return new ArrayList(Arrays.asList(oldStrs));
//			else
//				return new ArrayList();
//		}
//		return extractOffStrs(new ArrayList(Arrays.asList(oldStrs)), new ArrayList(Arrays.asList(newStrs)), force);
//	}

	public static List<Map> lineToCatalog(List<Map> srcList, String idName, String upIdName, String childrenName) {
		List<Map> aas = new ArrayList();
		Map<Integer, Map> am = new HashMap();
		while (true) {
			if (am.size() == srcList.size())
				break;
			for (Map a : srcList) {
				Integer id = (Integer) a.get(idName);
				Integer upId = (Integer) a.get(upIdName);
				if (am.get(id) != null)
					continue;
				a.put(childrenName, new ArrayList());

				if (upId == 0) {
					aas.add(a);
				} else {
					Map up = am.get(upId);
					if (up == null)
						continue;
					List children = (List) up.get(childrenName);
					children.add(a);
				}

				am.put(id, a);
			}
		}
		return aas;
	}

	public static boolean validUrlIs404(String url) {
		return true;
	}

	public static void main(String[] args) {
		System.out.println(getTodayRemainSecond());
	}

	public static Integer getTodayRemainSecond() {
		Date now = new Date();
		LocalDateTime midnight = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusDays(1)
				.withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime currentDateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
		long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
		return (int) seconds;
	}
}
