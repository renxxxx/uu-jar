package renx.uu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LList {

	public static void main(String[] args) {
		LList llist = LList.build();
		llist.add("1");
		llist.add("2");
		System.out.println(llist.list);
	}

	public int index = -1;
	public List list = null;

	public static LList build(List list) {
		if (list == null) {
			LList listt = new LList();
			return listt;
		} else {
			LList listt = new LList();
			listt.list = list;
			return listt;
		}
	}

	public static LList build() {
		return build(null);
	}

	public LList add(Object e, boolean if_) {
		if (if_)
			return add(e);
		return this;
	}

	public LList add(Object e) {
		if (this.list == null)
			this.list = new ArrayList();
		if (e instanceof MMap)
			e = ((MMap) e).map;
		else if (e instanceof LList)
			e = ((LList) e).list;
		this.list.add(e);
		return this;
	}

	public LList addAll(List e) {
		if (this.list == null)
			this.list = new ArrayList();
		this.list.addAll(e);
		return this;
	}

	public LList addAll(LList e) {
		if (this.list == null)
			this.list = new ArrayList();
		this.list.addAll(e.list);
		return this;
	}

	public LList addAll(List e, boolean if_) {
		if (if_)
			addAll(e);
		return this;
	}

	public LList addAll(LList e, boolean if_) {
		if (if_)
			addAll(e);
		return this;
	}

	public Object[] toArray() {
		if (this.list == null)
			return null;
		return this.list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		if (this.list == null)
			return null;
		return (T[]) this.list.toArray(a);
	}

	public Object get(int index) {
		if (this.list == null || this.list.size() <= index)
			return null;
		else
			return this.list.get(index);
	};

	public Object next() {
		if (this.list != null && (this.list.size() - 1) >= (this.index + 1))
			return get(++this.index);
		return null;
	};

	public void reset() {
		this.index = -1;
	};

	public int size() {
		if (this.list == null)
			return 0;
		else
			return this.list.size();
	};

	public boolean isEmpty() {
		if (this.list == null)
			return true;
		else
			return this.list.isEmpty();
	}

	public boolean notEmpty() {
		return !isEmpty();
	}

	public boolean isExist() {
		return notEmpty();
	}

	public String toString() {
		if (this.list == null)
			return null;
		else
			return this.list.toString();
	}
}
