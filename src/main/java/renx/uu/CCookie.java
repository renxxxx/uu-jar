package renx.uu;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CCookie {

	public Cookie o = null;

	public String name = null;

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
		if (o != null)
			ccookie.name = o.getName();
		return ccookie;
	}

	public static CCookie build(String name, String value) {
		CCookie ccookie = new CCookie();
		ccookie.o = new Cookie(name, value);
		ccookie.name = name;
		return ccookie;
	}

	public static CCookie build(String name) {
		CCookie ccookie = new CCookie();
		ccookie.name = name;
		return ccookie;
	}

	public CCookie domain(String domain) {
		if (o != null)
			o.setDomain(domain);
		return this;
	}

	public String domain() {
		return o != null ? o.getDomain() : null;
	}

	public CCookie path(String uri) {
		if (o != null)
			o.setPath(uri);
		return this;
	}

	public String path() {
		return o != null ? o.getPath() : null;
	}

	public CCookie httpOnly(boolean isHttpOnly) {
		if (o != null)
			o.setHttpOnly(isHttpOnly);
		return this;
	}

	public CCookie maxAge(int expiry) {
		if (o != null)
			o.setMaxAge(expiry);
		return this;
	}

	public CCookie value(HttpServletRequest request) {
		Map<String, Cookie> cookiem = Servletuu.cookiem(request);
		this.o = cookiem.get(name);
		return this;
	}

	public CCookie value(Cookie o) {
		this.o = o;
		return this;
	}

	public CCookie value(String newValue) {
		if (o != null)
			o.setValue(newValue);
		return this;
	}

	public String name() {
		return name;
	}

	public CCookie name(String newName) {
		name = newName;
		if (o != null) {
			Cookie o2 = new Cookie(newName, o.getValue());
			o2.setComment(o.getComment());
			o2.setDomain(o.getDomain());
			o2.setHttpOnly(o.isHttpOnly());
			o2.setMaxAge(o.getMaxAge());
			o2.setPath(o.getPath());
			o2.setSecure(o.getSecure());
			o2.setValue(o.getValue());
			o2.setVersion(o.getVersion());
			o = o2;
		}
		return this;
	}

	public String value() {
		return o != null ? o.getValue() : null;
	}

	public CCookie secure(boolean flag) {
		if (o != null)
			o.setSecure(flag);
		return this;
	}

	public CCookie addTo(HttpServletResponse response) {
		if (o != null)
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
