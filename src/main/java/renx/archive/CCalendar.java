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

	public Date time() {
		return this.real.getTime();
	}
}
