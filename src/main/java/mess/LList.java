package mess;

import java.util.ArrayList;
import java.util.List;

public class LList<E> {

	public List<E> list = null;

	public static <E> LList<E> build(List<E> list) {
		if (list == null) {
			LList<E> listt = new LList<E>();
			return listt;
		} else {
			LList<E> listt = new LList<E>();
			listt.list = list;
			return listt;
		}

	}

	public static <E> LList<E> build() {
		LList<E> listt = new LList<E>();
		listt.list = new ArrayList<E>();
		return listt;
	}

	public LList<E> add(E e) {
		list.add(e);
		return this;
	}
}
