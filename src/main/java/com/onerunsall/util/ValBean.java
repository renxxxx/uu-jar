package com.onerunsall.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValBean {
	private String name;
	private String code;
	private String value;
	private String separator = ",";
	private String datePattern = "yyyy-MM-dd HH:mm:ss";

	private Integer intValue;
	private Float floatValue;
	private Long longValue;
	private BigDecimal decimalValue;
	private Date dateValue;
	private String[] splitArrValue;

	private ValBean() {
	}

	public static CacheMap.Ccc<String, Pattern> regexCache = new CacheMap.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public static ValBean build(String name, String code, String value) {
		ValBean param = new ValBean();
		param.name = name;
		param.code = code;
		param.value = value;
		return param;
	}

	public ValBean suffix(String suffix) {
		if (this.value != null)
			this.value = this.value + suffix;
		return this;
	}

	public ValBean prefix(String prefix) {
		if (this.value != null)
			this.value = prefix + this.value;
		return this;
	}

	public ValBean trim() {
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public ValBean trimLeft() {
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public ValBean trimRight() {
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public String val() {
		return this.value;
	}

	public Integer toInteger() {
		if (this.intValue != null)
			return this.intValue;
		this.intValue = isEmpty() ? null : Integer.parseInt(this.value);
		return this.intValue;
	}

	public Float toFloat() {
		if (this.floatValue != null)
			return this.floatValue;
		this.floatValue = isEmpty() ? null : Float.parseFloat(this.value);
		return this.floatValue;
	}

	public Long toLong() {
		if (this.longValue != null)
			return this.longValue;
		this.longValue = isEmpty() ? null : Long.parseLong(this.value);
		return this.longValue;
	}

	public BigDecimal toDecimal() {
		if (this.decimalValue != null)
			return this.decimalValue;
		this.decimalValue = isEmpty() ? null : new BigDecimal(this.value);
		return this.decimalValue;
	}

	public Date toDate() {
		if (this.dateValue != null)
			return this.dateValue;
		this.dateValue = isEmpty() ? null : toDate(this.value, this.datePattern);
		return this.dateValue;
	}

	public ValBean setDatePattern(String datePattern) {
		this.datePattern = datePattern;
		return this;
	}

	public ValBean setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public String[] toSplitArr() {
		if (this.splitArrValue != null)
			return this.splitArrValue;
		this.splitArrValue = isNull() ? null
				: StringUtils.splitByWholeSeparatorPreserveAllTokens(this.value, this.separator);
		return this.splitArrValue;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public static void main(String[] args) {
		float a = 1.000000f;
		System.out.println((a + "").replaceAll("\\.0*$", ""));
	}

	public ValBean vNull() {
		if (this.value == null)
			throw Goback.failure(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public ValBean vNull(boolean todo) {
		if (todo)
			this.vNull();
		return this;
	}

	public ValBean vEmpty() {
		if ((this.value == null || this.value.isEmpty()))
			throw Goback.failure(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public ValBean vEmpty(boolean todo) {
		if (todo)
			this.vEmpty();
		return this;
	}

	public ValBean vBlank() {
		if ((this.value != null && this.value.isEmpty()))
			throw Goback.failure(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public ValBean vBlank(boolean todo) {
		if (todo)
			this.vBlank();
		return this;
	}

	public ValBean nullDef(String defaultValue) {
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isNull() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public ValBean nullDef(boolean todo, String defaultValue) {
		if (todo)
			this.nullDef(defaultValue);
		return this;
	}

	public ValBean blankDef(String defaultValue) {
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isBlank() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public ValBean blankDef(boolean todo, String defaultValue) {
		if (todo)
			this.blankDef(defaultValue);
		return this;
	}

	public ValBean emptyDef(String defaultValue) {
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isEmpty() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public ValBean emptyDef(boolean todo, String defaultValue) {
		if (todo)
			this.emptyDef(defaultValue);
		return this;
	}

	public ValBean vLen(int length) {
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() != length) {
			throw Goback.failure(1001, "\"" + this.name + "\"长度只能是" + length).setErrParam(this.code);
		}
		return this;
	}

	public ValBean vMaxLen(int length) {
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() > length) {
			throw Goback.failure(1001, "\"" + this.name + "\"长度最大" + length).setErrParam(this.code);
		}
		return this;
	}

	public ValBean vMaxNum(float maxnum) {
		if (!isEmpty() && toFloat() > maxnum) {
			throw Goback.failure(1001, "\"" + this.name + "\"最大" + maxnum).setErrParam(this.code);
		}
		return this;
	}

	public ValBean vMinNum(float minnum) {
		if (!isEmpty() && toFloat() < minnum) {
			throw Goback.failure(1001, "\"" + this.name + "\"最小" + minnum).setErrParam(this.code);
		}
		return this;
	}

	public boolean isEmpty() {
		if (this.value == null || this.value.isEmpty())
			return true;
		return false;
	}

	public boolean isNull() {
		if (this.value == null)
			return true;
		return false;
	}

	public boolean isBlank() {
		if (this.value != null && this.value.isEmpty())
			return true;
		return false;
	}

	public ValBean vMinLen(int length) {
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() < length) {
			throw Goback.failure(1001, "\"" + this.name + "\"长度最低" + length).setErrParam(this.code);
		}
		return this;
	}

	public ValBean vLenRange(int min, int max) {
		vMinLen(min);
		vMaxLen(max);
		return this;
	}

	public ValBean vReg(String regex) {
		return vReg(regex, null);
	}

	public ValBean clear() {
		this.splitArrValue = null;
		this.dateValue = null;
		this.intValue = null;
		this.floatValue = null;
		this.longValue = null;
		this.decimalValue = null;
		return this;
	}

	public ValBean vReplace(String regex, String replace) {
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty() && replace != null)
			this.value = regexCache.getWithCreate(regex).matcher(this.value).replaceAll(replace);
		return this;
	}

	public ValBean vReg(String regex, String note) {
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw Goback
						.failure(1001,
								"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note))
						.setErrParam(this.code);
		}
		return this;
	}

	public static Integer toInteger(Object value) {
		if (value == null)
			return null;
		if (value instanceof Integer)
			return (Integer) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Integer(valueStr);
	}

	public static Float toFloat(Object value) {
		if (value == null)
			return null;
		if (value instanceof Float)
			return (Float) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Float(valueStr);
	}

	public static String toString(Object value) {
		if (value == null)
			return null;
		if (value instanceof String)
			return (String) value;
		String valueStr = value.toString();
		if (valueStr.isEmpty())
			return null;
		else
			return valueStr.toString();
	}

	public static Date toDate(Object value) {
		if (value == null)
			return null;
		if (value instanceof Date)
			return (Date) value;
		if (value instanceof String) {
			if (value.toString().trim().isEmpty())
				return null;
			if (value.toString().trim().length() == 13 && StringUtils.isNumeric(value.toString()))
				return new Date(Long.parseLong(value.toString().trim()));
			if (value.toString().trim().length() == 10 && StringUtils.isNumeric(value.toString()))
				return new Date(Long.parseLong(value.toString().trim()) * 1000);
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
			} catch (Exception e) {
			}
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
			} catch (Exception e) {
			}
			try {
				return new SimpleDateFormat("HH:mm:ss").parse(value.toString());
			} catch (Exception e) {
			}
		}
		if (value instanceof Long)
			return new Date((Long) value);
		if (value instanceof Integer)
			return new Date((Long) value * 1000);
		return null;
	}

	public static Date toDate(Object value, String pattern) {
		Date date = toDate(value);
		if (date == null)
			try {
				date = new SimpleDateFormat(pattern).parse(value.toString());
			} catch (Exception e) {
			}
		return date;
	}

	public static Long toLong(Object value) {
		if (value == null)
			return null;
		if (value instanceof Long)
			return (Long) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Long(valueStr);
	}

	public static BigDecimal toDecimal(Object value) {
		if (value == null)
			return null;
		if (value instanceof BigDecimal)
			return (BigDecimal) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new BigDecimal(valueStr);
	}
}
