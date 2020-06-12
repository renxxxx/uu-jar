package com.inininininin.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Value2 {
	private static Logger logger = Logger.getLogger(Value2.class);

	private String name;
	private String code;
	private String value;
	private String separator = ",";
	private static String datePattern = "yyyy-MM-dd HH:mm:ss.SSS Z";
	boolean todo = true;
	private Integer intValue;
	private Float floatValue;
	private Long longValue;
	private BigDecimal decimalValue;
	private Date dateValue;
	private String[] splitArrValue;

	private Value2() {

	}

	public static CacheMap.Ccc<String, Pattern> regexCache = new CacheMap.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public Value2 clear() {
		this.splitArrValue = null;
		this.dateValue = null;
		this.intValue = null;
		this.floatValue = null;
		this.longValue = null;
		this.decimalValue = null;
		return this;
	}

	public static Value2 build(String name, String code, String... values) {
		logger.info("in -> name : " + name + " code : " + code + " values : " + Arrays.toString(values));
		Value2 param = new Value2();
		param.name = name;
		param.code = code;
		if (values != null)
			for (String value : values) {
				if (value != null) {
					param.value = value;
					break;
				}
			}

		if (param.value != null && !param.value.isEmpty()
				&& param.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)>.*"))
			throw ModuleResponse.response(1001, "\"" + param.name + "\"有误").setErrParam(param.code);

		return param;
	}

	public Value2 suffix(String suffix) {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value + suffix;
		return this;
	}

	public Value2 prefix(String prefix) {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = prefix + this.value;
		return this;
	}

	public Value2 trim() {
		if (!this.todo)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Value2 trimToNull() {
		if (!this.todo)
			return this;
		if (this.value != null && this.value.trim().isEmpty())
			this.value = null;
		return this;
	}

	public Value2 trimToBlank() {
		if (!this.todo)
			return this;
		if (this.value == null)
			this.value = "";
		this.value = this.value.trim();
		return this;
	}

	public Value2 trimLeft() {
		if (!this.todo)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Value2 trimRight() {
		if (!this.todo)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Value2 setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public Value2 nullDef(String defaultValue) {
		if (!this.todo)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isNull() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Value2 nullDef(boolean todo, String defaultValue) {
		if (!this.todo)
			return this;
		if (todo)
			this.nullDef(defaultValue);
		return this;
	}

	public Value2 blankDef(String defaultValue) {
		if (!this.todo)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isBlank() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Value2 blankDef(boolean todo, String defaultValue) {
		if (!this.todo)
			return this;
		if (todo)
			this.blankDef(defaultValue);
		return this;
	}

	public Value2 emptyDef(String defaultValue) {
		if (!this.todo)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isEmpty() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Value2 emptyDef(boolean todo, String defaultValue) {
		if (!this.todo)
			return this;
		if (todo)
			this.emptyDef(defaultValue);
		return this;
	}

	public static void main(String[] args) throws ParseException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {

		System.out.println(Value2.build(null, null, "5").vEnum("3", "4", "5"));

	}

	public Value2 stop(boolean todo) {
		if (todo)
			this.todo = false;
		return this;
	}

	public Value2 vNull() {
		if (!this.todo)
			return this;
		if (this.value == null)
			throw ModuleResponse.response(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Value2 vNull(boolean todo) {
		if (!this.todo)
			return this;
		if (todo)
			this.vNull();
		return this;
	}

	public Value2 vEmpty() {
		if (!this.todo)
			return this;
		if ((this.value == null || this.value.isEmpty()))
			throw ModuleResponse.response(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Value2 vEmpty(boolean todo) {
		if (!this.todo)
			return this;
		if (todo)
			this.vEmpty();
		return this;
	}

	public Value2 vBlank() {
		if (!this.todo)
			return this;
		if ((this.value != null && this.value.isEmpty()))
			throw ModuleResponse.response(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Value2 vBlank(boolean todo) {
		if (!this.todo)
			return this;
		if (todo)
			this.vBlank();
		return this;
	}

	public Value2 vLen(int length) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() != length) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"长度只能是" + length).setErrParam(this.code);
		}
		return this;
	}

	public Value2 vLen(boolean todo, int length) {
		if (!this.todo)
			return this;
		if (todo)
			this.vLen(length);
		return this;
	}

	public Value2 vMinLen(int length) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() < length) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"长度最低" + length).setErrParam(this.code);
		}
		return this;
	}

	public Value2 vMinLen(boolean todo, int length) {
		if (!this.todo)
			return this;
		if (todo)
			this.vMinLen(length);
		return this;
	}

	public Value2 vMaxLen(int length) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() > length) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"长度最大" + length).setErrParam(this.code);
		}
		return this;
	}

	public Value2 vMaxLen(boolean todo, int length) {
		if (!this.todo)
			return this;
		if (todo)
			this.vMaxLen(length);
		return this;
	}

	public Value2 vMaxNum(float maxnum) {
		if (!this.todo)
			return this;
		if (!isEmpty() && toFloat() > maxnum) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"最大" + maxnum).setErrParam(this.code);
		}
		return this;
	}

	public Value2 vMaxNum(boolean todo, float maxnum) {
		if (!this.todo)
			return this;
		if (todo)
			this.vMaxNum(maxnum);
		return this;
	}

	public Value2 vMinNum(float minnum) {
		if (!this.todo)
			return this;
		if (!isEmpty() && toFloat() < minnum) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"最小" + minnum).setErrParam(this.code);
		}
		return this;
	}

	public Value2 vMinNum(boolean todo, float minnum) {
		if (!this.todo)
			return this;
		if (todo)
			this.vMinNum(minnum);
		return this;
	}

	public Value2 vMaxCount(int count) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && this.toSplitArr() != null
				&& this.toSplitArr().length > count) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"最多" + count + "个").setErrParam(this.code);
		}
		return this;
	}

	public Value2 vInteger() {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			Integer l = null;
			try {
				l = Integer.parseInt(this.value);
			} catch (Exception e) {

			}
			if (l == null)
				throw ModuleResponse.response(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Value2 vBoolean() {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && !"1".equals(this.value) && !"0".equals(this.value)) {
			throw ModuleResponse.response(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Value2 vEnum(String... values) {
		if (!this.todo)
			return this;
		boolean v = false;
		if (this.value != null && !this.value.isEmpty()) {
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (value == this.value)
					v = true;
				else if (value != null && value.equals(this.value)) {
					v = true;
				}
			}
		} else {
			v = true;
		}
		if (!v)
			throw ModuleResponse.response(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		else
			return this;
	}

	public Value2 vLong() {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			Long l = null;
			try {
				l = Long.parseLong(this.value);
			} catch (Exception e) {

			}
			if (l == null)
				throw ModuleResponse.response(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Value2 vDouble() {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			Double l = null;
			try {
				l = Double.parseDouble(this.value);
			} catch (Exception e) {

			}
			if (l == null)
				throw ModuleResponse.response(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
	}

	public Value2 vFloat() {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			Float l = null;
			try {
				l = Float.parseFloat(this.value);
			} catch (Exception e) {

			}
			if (l == null)
				throw ModuleResponse.response(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
	}

	public Value2 vDecimal() {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			BigDecimal l = null;
			try {
				l = new BigDecimal(this.value);
			} catch (Exception e) {

			}
			if (l == null)
				throw ModuleResponse.response(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
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

	public Value2 vLenRange(int min, int max) {
		if (!this.todo)
			return this;
		vMinLen(min);
		vMaxLen(max);
		return this;
	}

	public Value2 vNumRange(float min, float max) {
		if (!this.todo)
			return this;
		vMinNum(min);
		vMaxNum(max);
		return this;
	}

	public Value2 vReg(Pattern regex) {
		if (!this.todo)
			return this;
		return vReg(regex, null);
	}

	public Value2 vReg(Pattern regex, String note) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (!regex.matcher(this.value).matches())
				throw ModuleResponse
						.response(1001,
								"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note))
						.setErrParam(this.code);
		}
		return this;
	}

	public Value2 vRegNot(Pattern regex) {
		if (!this.todo)
			return this;
		return vRegNot(regex, null);
	}

	public Value2 vRegNot(Pattern regex, String note) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (regex.matcher(this.value).matches())
				throw ModuleResponse
						.response(1001,
								"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note))
						.setErrParam(this.code);
		}
		return this;
	}

	public Value2 vReg(String regex) {
		if (!this.todo)
			return this;
		return vReg(regex, null);
	}

	public Value2 vReg(String regex, String note) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw ModuleResponse
						.response(1001,
								"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note))
						.setErrParam(this.code);
		}
		return this;
	}

	public Value2 vRegNot(String regex) {
		if (!this.todo)
			return this;
		return vRegNot(regex, null);
	}

	public Value2 vRegNot(String regex, String note) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw ModuleResponse
						.response(1001,
								"\"" + this.name + "\"有误" + (note == null || note.isEmpty() ? "" : ",要求：" + note))
						.setErrParam(this.code);
		}
		return this;
	}

	public Value2 vReplace(String regex, String replace) {
		if (!this.todo)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty() && replace != null)
			this.value = regexCache.getWithCreate(regex).matcher(this.value).replaceAll(replace);
		return this;
	}

	public Value2 vReplace(String regex, String replace, boolean todo) {
		if (!this.todo)
			return this;
		if (todo)
			return vReplace(regex, replace);
		return this;
	}

	public String val() {
		return this.value;
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
		return this.val();
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

	public String formatDate(String pattern) {
		Date data = this.toDate();
		return new SimpleDateFormat(pattern).format(data);
	}

	public Value2 vDate() {
		if (this.value == null || this.value.isEmpty())
			return this;
		toDate();
		if (this.dateValue == null)
			throw ModuleResponse.response(1001, "\"" + this.name + "\"请输入日期").setErrParam(this.code);
		else {
			this.value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(this.dateValue);
		}
		return this;
	}

	public Date toDate() {
		if (this.dateValue != null)
			return this.dateValue;
		this.dateValue = isEmpty() ? null : toDate(this.value);
		return this.dateValue;
	}

	public void bomb(String message) {
		throw ModuleResponse.response(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public void bomb(boolean todo, String message) {
		if (todo)
			throw ModuleResponse.response(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public boolean equals(String object) {
		if (this.value == object)
			return true;
		if (this.value == null && object != null)
			return false;
		return this.value.equals(object);
	}

	public Value2 toLowerCase() {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value.toLowerCase();
		return this;
	}

	public Value2 toUpperCase() {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value.toUpperCase();
		return this;
	}

	public Value2 replaceAll(String regex, String replacement) {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value.replaceAll(regex, replacement);
		return this;
	}

	public Value2 substring(int beginIndex, int endIndex) {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex, endIndex);
		return this;
	}

	public Value2 substring(int beginIndex) {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex);
		return this;
	}

	public Value2 concat(String str) {
		if (!this.todo)
			return this;
		if (this.value != null)
			this.value = this.value.concat(str);
		return this;
	}

	public boolean contains(String prefix) {
		if (this.value != null)
			return this.value.contains(prefix);
		return false;
	}

	public boolean startsWith(String prefix) {
		if (this.value != null)
			return this.value.startsWith(prefix);
		return false;
	}

	public boolean endsWith(String suffix) {
		if (this.value != null)
			return this.value.endsWith(suffix);
		return false;
	}

	public Value2 set(String value) {
		clear();
		this.value = value;
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
		Date date = null;
		if (value == null)
			return null;
		if (value instanceof Date)
			return (Date) value;
		try {
			date = new SimpleDateFormat(datePattern).parse(value.toString());
			if (date != null) {
				return date;
			}
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

	public static Object attr(Object target, Object... keyArray)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		for (int i = 0; i < keyArray.length; i++) {
			if (target instanceof Map) {
				target = ((Map) target).get(keyArray[i]);
			} else if (target instanceof String) {

			} else if (target instanceof Object) {
				Field f = Object.class.getDeclaredField(keyArray[i].toString());
				f.setAccessible(true);
				target = f.get(target);
			}

			if (target == null)
				break;
		}
		return target;
	}

}
