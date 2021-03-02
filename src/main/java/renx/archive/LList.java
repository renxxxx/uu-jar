package renx.archive;

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
		return build(null);
	}

	public LList<E> add(E e) {
		list.add(e);
		return this;
	}

	public E get(int index) {
		if (this.list == null || this.list.size() <= index)
			return null;
		else
			return this.list.get(index);
	};

	public int size() {
		if (this.list == null)
			return 0;
		else
			return this.list.size();
	};

	public boolean isEmpty() {
		if (this.list == null)
			return false;
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
