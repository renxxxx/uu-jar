package renx.uu;

import javax.servlet.http.Cookie;

public class CCookie {

	public Cookie o = null;

	public static CCookie build(Cookie o) {
		CCookie ccookie = new CCookie();
		ccookie.o = o;
		return ccookie;
	}

	public String val() {
		return o != null ? o.getValue() : null;
	}
}
