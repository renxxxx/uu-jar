package renx.uu;

import java.util.ArrayList;
import java.util.List;

public class LList<E> {

	public static void main(String[] args) {
		LList llist = LList.build();
		llist.add("1");
		llist.add("2");
		System.out.println(llist.list);
	}

	public int index = -1;
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
		return build(null);
	}

	public LList<E> addif(E e, boolean iff) {
		if (iff)
			return add(e);
		return this;
	}

	public LList<E> add(E e) {
		if (this.list == null)
			this.list = new ArrayList<E>();
		this.list.add(e);
		return this;
	}

	public Object[] toArray() {
		if (this.list == null)
			return null;
		return this.list.toArray();
	}

	public E get(int index) {
		if (this.list == null || this.list.size() <= index)
			return null;
		else
			return this.list.get(index);
	};

	public E next() {
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

	public String toString() {
		if (this.list == null)
			return null;
		else
			return this.list.toString();
	}
}
