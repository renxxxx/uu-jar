package com.giveup;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Param {
	private String name;
	private String code;
	private String value;
	private String datePattern;

	private Param() {
	}

	public static CacheMap.Ccc<String, Pattern> regexCache = new CacheMap.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public static Param build(String name, String code, String value) {
		Param param = new Param();
		param.name = name;
		param.code = code;
		param.value = value == null ? null : value.trim();
		return param;
	}

	public static Param buildToNull(String name, String code, String value) {
		Param param = new Param();
		param.name = name;
		param.code = code;
		param.value = value == null ? null : value.trim();
		param.value = (value != null && value.isEmpty()) ? null : value;
		return param;
	}

	public Param trim() {
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public String val() {
		return this.value;
	}

	public Integer toInteger() {
		return isEmpty() ? null : Integer.parseInt(this.value);
	}

	public Float toFloat() {
		return isEmpty() ? null : Float.parseFloat(this.value);
	}

	public Long toLong() {
		return isEmpty() ? null : Long.parseLong(this.value);
	}

	public BigDecimal toDecimal() {
		return isEmpty() ? null : new BigDecimal(this.value);
	}

	public Date toDate() {
		if (this.value != null)
			try {
				return new SimpleDateFormat(this.datePattern).parse(this.value);
			} catch (ParseException e) {
				return null;
			}
		return null;
	}

	public String[] toCommaSplitArr() {
		return toSplitArr(",");
	}

	public String[] toSplitArr(String separator) {
		return this.value == null ? null : StringUtils.splitByWholeSeparatorPreserveAllTokens(this.value, separator);
	}

	@Override
	public String toString() {
		return this.value;
	}
//	public static String paramv(String name, String value, boolean nullIs, String defaultValue, int length,
//			String regex) {
//		value = value == null ? value : value.trim();
//		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
//		if (!nullIs && (value == null || value.isEmpty()))
//			throw new InteractRuntimeException(1001, name + "不能空");
//		if ((value == null || value.isEmpty()) && defaultValue != null && !defaultValue.isEmpty())
//			value = defaultValue;
//		if (value != null && !value.isEmpty() && length > -1 && value.length() > length) {
//			throw new InteractRuntimeException(1001, name + "最大长度" + length);
//		}
//		if (value != null && !value.isEmpty() && regex != null && !regex.isEmpty()) {
//			if (!Sc.regexCache.getWithCreate(regex).matcher(value).matches())
//				throw new InteractRuntimeException(1001, name + "有误");
//		}
//		return value;
//	}

	public Param vDate(String pattern) {
		return vDate(pattern, null);
	}

	public static void main(String[] args) {
		float a = 1.000000f;
		System.out.println((a + "").replaceAll("\\.0*$", ""));
	}

	public Param vDate(String pattern, String note) {
		this.datePattern = pattern;
		if (this.value != null && !this.value.isEmpty())
			try {
				new SimpleDateFormat(pattern).parse(this.value);
			} catch (Exception e) {
				throw new InteractRuntimeException(1001, this.code,
						"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note));
			}
		return this;
	}

	public Param vNull() {
		if (this.value == null)
			throw new InteractRuntimeException(1001, this.code, "\"" + this.name + "\"不能空");
		return this;
	}

	public Param vEmpty() {
		if ((this.value == null || this.value.isEmpty()))
			throw new InteractRuntimeException(1001, this.code, "\"" + this.name + "\"不能空");
		return this;
	}

	public Param vBlank() {
		if ((this.value != null && this.value.isEmpty()))
			throw new InteractRuntimeException(1001, this.code, "\"" + this.name + "\"不能空");
		return this;
	}

	public Param def(String defaultValue) {
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (this.value == null && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Param defInEmpty(String defaultValue) {
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isEmpty() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Param vLen(int length) {
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() != length) {
			throw new InteractRuntimeException(1001, this.code, "\"" + this.name + "\"长度只能是" + length);
		}
		return this;
	}

	public Param vMaxLen(int length) {
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() > length) {
			throw new InteractRuntimeException(1001, this.code, "\"" + this.name + "\"长度最大" + length);
		}
		return this;
	}

	public Param vMaxNum(float maxnum) {
		if (!isEmpty() && toFloat() > maxnum) {
			throw new InteractRuntimeException(1001, this.code,
					"\"" + this.name + "\"最大" + (maxnum + "").replaceAll("\\.0*$", ""));
		}
		return this;
	}

	public Param vMinNum(float minnum) {
		if (!isEmpty() && toFloat() < minnum) {
			throw new InteractRuntimeException(1001, this.code,
					"\"" + this.name + "\"最小" + (minnum + "").replaceAll("\\.0*$", ""));
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

	public Param vMinLen(int length) {
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() < length) {
			throw new InteractRuntimeException(1001, this.code, "\"" + this.name + "\"长度最低" + length);
		}
		return this;
	}

	public Param vLenRange(int min, int max) {
		vMinLen(min);
		vMaxLen(max);
		return this;
	}

	public Param vReg(String regex) {
		return vReg(regex, null);
	}

	public Param set(String value) {
		this.value = value;
		return this;
	}

	public Param vReplace(String regex, String replace) {
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty() && replace != null)
			this.value = regexCache.getWithCreate(regex).matcher(this.value).replaceAll(replace);
		return this;
	}

	public Param vReg(String regex, String note) {
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw new InteractRuntimeException(1001, this.code,
						"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note));
		}
		return this;
	}

}
