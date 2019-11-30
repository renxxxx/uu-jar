package com.oneall.util;

import java.util.List;
import java.util.Map;

public class ChainList<E> {

	private List<E> list = null;

	public ChainList(List<E> list) {
		super();
		this.list = list;
	}

	public ChainList<E> put(E value) {
		list.add(value);
		return this;
	}

	public List<E> appear() {
		return list;
	}
}
