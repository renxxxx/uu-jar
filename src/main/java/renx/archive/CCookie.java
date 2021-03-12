package renx.archive;

import javax.servlet.http.Cookie;

public class CCookie<K, V> {

	public Cookie o = null;

	public CCookie(Cookie o) {
		super();
		this.o = o;
	}

	public String v() {
		return o != null ? o.getValue() : null;
	}
}
