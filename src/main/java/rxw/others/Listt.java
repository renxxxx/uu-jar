package rxw.others;

import java.util.ArrayList;
import java.util.List;

public class Listt<E> {

	public List<E> list = null;

	public static <E> Listt<E> instance(List<E> list) {
		Listt<E> listt = new Listt<E>();
		listt.list = list;
		return listt;
	}

	public static <E> Listt<E> instance() {
		Listt<E> listt = new Listt<E>();
		listt.list = new ArrayList<E>();
		return listt;
	}

	public Listt<E> add(E e) {
		list.add(e);
		return this;
	}
}
