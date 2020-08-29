package inininininin.util;

import java.util.List;

public class Listt<E> {

	public List<E> list = null;

	public Listt(List<E> list) {
		super();
		this.list = list;
	}

	public Listt<E> add(E e) {
		list.add(e);
		return this;
	}
}
