package renx.archive;

import java.util.Calendar;
import java.util.Date;

public class CCalendar {
	public Calendar realsrc;
	public Calendar real;

	public static CCalendar build() {
		CCalendar c = new CCalendar();
		c.realsrc = c.real = Calendar.getInstance();
		return c;
	}

	public static CCalendar build(Date time) {
		CCalendar c = new CCalendar();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		c.real = c.realsrc = calendar;
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
}
