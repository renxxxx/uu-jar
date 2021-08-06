package renx.uu;

import javax.servlet.http.Cookie;

public class CCookie<K, V> {

	public Cookie o = null;

	public CCookie(Cookie o) {
		super();
		this.o = o;
	}

	public String val() {
		return o != null ? o.getValue() : null;
	}
}
