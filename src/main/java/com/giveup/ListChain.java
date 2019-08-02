package com.giveup;

import java.util.List;
import java.util.Map;

public class ListChain<E> {

	private List<E> list = null;

	public ListChain(List<E> list) {
		super();
		this.list = list;
	}

	public ListChain<E> put(E value) {
		list.add(value);
		return this;
	}

	public List<E> appear() {
		return list;
	}
}
