package cc.renx.uu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DDate extends Date {
	public Calendar calendar = Calendar.getInstance();

	public static void main(String[] args) throws ParseException {
		DDate ddate = DDate.build().trim("yyyy-MM-dd 00:00:00.000");
		System.out.println(ddate.getTime());
	}

	public DDate(Long time) {
		set(time);
	}

	public DDate(String source) {
		set(source);
	}

	public DDate() {

	}

	public DDate AddSecs(int value) {
		calendar.add(Calendar.SECOND, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SubSecs(int value) {
		calendar.add(Calendar.SECOND, -value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate AddMins(int value) {
		calendar.add(Calendar.MINUTE, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SubMins(int value) {
		calendar.add(Calendar.MINUTE, -value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate AddHours(int value) {
		calendar.add(Calendar.HOUR_OF_DAY, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SubHours(int value) {
		calendar.add(Calendar.HOUR_OF_DAY, -value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate AddDays(int value) {
		calendar.add(Calendar.DAY_OF_MONTH, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SubDays(int value) {
		calendar.add(Calendar.DAY_OF_MONTH, -value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate AddMons(int value) {
		calendar.add(Calendar.MONTH, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SubMons(int value) {
		calendar.add(Calendar.MONTH, -value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate AddYears(int value) {
		calendar.add(Calendar.YEAR, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SubYears(int value) {
		calendar.add(Calendar.YEAR, -value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SetSecond(int value) {
		calendar.set(Calendar.SECOND, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SetMinute(int value) {
		calendar.set(Calendar.MINUTE, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SetHour(int value) {
		calendar.set(Calendar.HOUR_OF_DAY, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SetDay(int value) {
		calendar.set(Calendar.DAY_OF_MONTH, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SetMonth(int value) {
		calendar.set(Calendar.MONTH, value - 1);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate SetYear(int value) {
		calendar.set(Calendar.YEAR, value);
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

	public DDate set(Long time) {
		if (time == null)
			return this;
		this.setTime(time);
		calendar.setTime(this);
		return this;
	}

	public DDate set(String source) {
		if (source == null || source.isEmpty())
			return this;
		Date date = null;
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}

		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}

		if (date == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			try {
				date = new Date(Long.parseLong(source));
			} catch (Exception e) {
			}
		}
		if (date != null) {
			this.setTime(date.getTime());
			calendar.setTime(this);
		}
		return this;
	}

	public static DDate build() {
		return new DDate();
	}

	public static DDate build(Long time) {
		if (time == null)
			return null;
		return new DDate(time);
	}

	public static DDate build(String source) {
		if (source == null)
			return null;
		return new DDate(source);
	}

	public String format(String pattern) {
		return new SimpleDateFormat(pattern).format(this);
	}

	public DDate trim(String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		calendar.setTime(sdf.parse(sdf.format(this)));
		this.setTime(calendar.getTimeInMillis());
		return this;
	}

}
