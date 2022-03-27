package renx.uu;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CCookie {

	public Cookie o = null;

	public static void main(String[] args) {
		new Cookie("asdf", null);
	}

	public static CCookie build() {
		CCookie ccookie = new CCookie();
		return ccookie;
	}

	public static CCookie build(Cookie o) {
		CCookie ccookie = new CCookie();
		ccookie.o = o;
		return ccookie;
	}

	public static CCookie build(String name, String value) {
		CCookie ccookie = new CCookie();
		ccookie.o = new Cookie(name, value);
		return ccookie;
	}

	public CCookie domain(String domain) {
		o.setDomain(domain);
		return this;
	}

	public String domain() {
		return o != null ? o.getDomain() : null;
	}

	public CCookie path(String uri) {
		o.setPath(uri);
		return this;
	}

	public String path() {
		return o != null ? o.getPath() : null;
	}

	public CCookie httpOnly(boolean isHttpOnly) {
		o.setHttpOnly(isHttpOnly);
		return this;
	}

	public CCookie maxAge(int expiry) {
		o.setMaxAge(expiry);
		return this;
	}

	public CCookie value(Cookie o) {
		this.o = o;
		return this;
	}

	public CCookie value(String newValue) {
		o.setValue(newValue);
		return this;
	}

	public String value() {
		return o != null ? o.getValue() : null;
	}

	public CCookie secure(boolean flag) {
		o.setSecure(flag);
		return this;
	}

	public CCookie addTo(HttpServletResponse response) {
		response.addCookie(o);
		return this;
	}

	public boolean isEmpty() {
		if (o == null || o.getValue() == null || o.getValue().isEmpty())
			return true;
		return false;
	}

	public boolean isExisting() {
		return !isEmpty();
	}
}
