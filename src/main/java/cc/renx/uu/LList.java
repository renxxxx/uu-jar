package cc.renx.uu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class LList<E> {

	public static void main(String[] args) {
		LList llist = LList.build();
		llist.add("1");
		llist.add("2");
		System.out.println(llist.list);
	}

	public int index = -1;
	public List list = new ArrayList();

	public LList attrs(String key) {
		if (this.list == null)
			this.list = new ArrayList();

		LList values = LList.build();
		for (int i = 0; i < this.list.size(); i++) {
			MMap m = this.getMap(i);
			Object value = m.get(key);
			values.add(value);
		}
		return values;
	}

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

	public LList remove(int i) {
		if (this.list != null)
			this.list.remove(i);
		return this;
	}

	public LList addIf(int i, Object e, boolean if_) {
		if (if_)
			return add(i, e);
		return this;
	}

	public LList addIf(Object e, boolean if_) {
		if (if_)
			return add(e);
		return this;
	}

	public LList add(int i, Object e) {
		if (this.list == null)
			this.list = new ArrayList();
		if (e instanceof MMap)
			e = ((MMap) e).map;
		else if (e instanceof LList)
			e = ((LList) e).list;
		else if (e instanceof Var)
			e = ((Var) e).value;
		this.list.add(i, e);
		return this;
	}

	public LList add(Object e) {
		if (this.list == null)
			this.list = new ArrayList();
		if (e instanceof MMap)
			e = ((MMap) e).map;
		else if (e instanceof LList)
			e = ((LList) e).list;
		else if (e instanceof Var)
			e = ((Var) e).value;
		this.list.add(e);
		return this;
	}

	public LList addAll(Object[] e) {
		if (this.list == null)
			this.list = new ArrayList();
		if (e == null)
			return this;
		this.list.addAll(Arrays.asList(e));
		return this;
	}

	public LList addAll(List e) {
		if (this.list == null)
			this.list = new ArrayList();
		if (e != null)
			this.list.addAll(e);
		return this;
	}

	public LList addAll(LList e) {
		if (this.list == null)
			this.list = new ArrayList();
		if (e.list != null)
			this.list.addAll(e.list);
		return this;
	}

	public LList addAllIf(Object[] e, boolean if_) {
		if (if_)
			addAll(e);
		return this;
	}

	public LList addAllIf(List e, boolean if_) {
		if (if_)
			addAll(e);
		return this;
	}

	public LList addAllIf(LList e, boolean if_) {
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

	public Object getObject(int index) {
		if (this.list == null || this.list.isEmpty() || this.list.size() <= index)
			return null;
		else {
			Object item = this.list.get(index);
			if (item instanceof MMap)
				item = ((MMap) item).map;
			else if (item instanceof LList)
				item = ((LList) item).list;
			else if (item instanceof Var)
				item = ((Var) item).value;
			return item;
		}
	};

	public MMap getMap(int index) {
		if (this.list == null || this.list.isEmpty() || this.list.size() <= index)
			return MMap.build();
		else
			return MMap.build((Map) this.list.get(index));
	};

	public LList getList(int index) {
		if (this.list == null || this.list.isEmpty() || this.list.size() <= index)
			return LList.build();
		else
			return LList.build((List) this.list.get(index));
	};

	public String getString(int index) {
		if (this.list == null || this.list.isEmpty() || this.list.size() <= index)
			return null;
		else
			return Var.toString(this.list.get(index));
	};

	public Object next() {
		if (this.list != null && (this.list.size() - 1) >= (this.index + 1))
			return get(++this.index);
		return null;
	};

	public String nextString() {
		if (this.list != null && (this.list.size() - 1) >= (this.index + 1))
			return getString(++this.index);
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

	public boolean isExisting() {
		return notEmpty();
	}

	public String toString() {
		if (this.list == null)
			return null;
		else
			return this.list.toString();
	}

	public String join() {
		if (this.list == null)
			return null;
		return StringUtils.join(this.list);
	}

	public String join(String separator) {
		if (this.list == null)
			return null;
		return StringUtils.join(this.list, separator);
	}

	public Var get(int index) {
		if (this.list == null || this.list.isEmpty() || this.list.size() <= index)
			return Var.build();
		return Var.build(this.list.get(index));
	}
}