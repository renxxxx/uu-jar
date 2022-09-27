package cc.renx.uu;

import java.util.Calendar;
import java.util.Date;

public class CCalendar {
	public Calendar realsrc;
	public Calendar real;

	public static CCalendar build() {
		CCalendar c = new CCalendar();
		c.real = Calendar.getInstance();
		c.realsrc = Calendar.getInstance();
		c.realsrc.setTime(c.real.getTime());
		return c;
	}

	public static CCalendar build(Date time) {
		CCalendar c = new CCalendar();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		c.real = calendar;
		c.realsrc = Calendar.getInstance();
		c.realsrc.setTime(c.real.getTime());
		return c;
	}

	public CCalendar add(int field, int amount) {
		this.real.add(field, amount);
		return this;
	}

	public CCalendar set(int field, int value) {
		this.real.set(field, value);
		return this;
	}

	public CCalendar settime(int hour, int minute, int second, int millisecond) {
		set(Calendar.HOUR_OF_DAY, hour).set(Calendar.MINUTE, minute).set(Calendar.SECOND, second)
				.set(Calendar.MILLISECOND, millisecond);
		return this;
	}

	public Date time() {
		return this.real.getTime();
	}

	public CCalendar reset() {
		this.real = Calendar.getInstance();
		this.real.setTime(this.realsrc.getTime());
		return this;
	}
}
