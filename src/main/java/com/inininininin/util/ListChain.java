package com.inininininin.util;

import java.util.List;

public class ListChain<E> {

	public List<E> list = null;

	public ListChain(List<E> list) {
		super();
		this.list = list;
	}

	public ListChain<E> add(E e) {
		list.add(e);
		return this;
	}
}
