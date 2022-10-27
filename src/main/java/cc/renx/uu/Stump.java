package cc.renx.uu;

public class Stump extends RuntimeException {

	public Throwable src = null;

	private Stump() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Stump(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	private Stump(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	private Stump(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	private Stump(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public static RuntimeException build(String msg, Throwable e) {
		if (e instanceof Result) {
			return (Result) e;
		} else if (e instanceof Stump) {
			Stump stump = new Stump(msg, e);
			stump.src = ((Stump) e).src;
			return stump;
		} else {
			Stump stump = new Stump(msg, e);
			stump.src = e;
			return stump;
		}
	}

	public static RuntimeException build(Throwable e) {
		if (e instanceof Result) {
			return (Result) e;
		} else if (e instanceof Stump) {
			Stump stump = new Stump(e);
			stump.src = ((Stump) e).src;
			return stump;
		} else {
			Stump stump = new Stump(e);
			stump.src = e;
			return stump;
		}
	}
}
