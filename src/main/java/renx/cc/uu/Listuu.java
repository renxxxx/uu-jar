package renx.cc.uu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listuu {
	private static Logger logger = LoggerFactory.getLogger(Listuu.class);

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

	public static List subLeft(List list, int count) {
		List list2 = new ArrayList();
		for (int i = count - 1; i >= 0; i--) {
			if (list.size() - 1 >= i)
				list2.add(0, list.remove(i));
		}
		return list2;
	}

	public static List subRight(List list, int count) {
		List list2 = new ArrayList();
		int size = list.size();
		for (int i = size - 1; i >= size - count + 1; i--) {
			list2.add(0, list.remove(i));
		}
		return list2;
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
