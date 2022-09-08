package renx.uu;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Var {
	private static Logger logger = LoggerFactory.getLogger(Var.class);

	public String name;
	public String code;
	public String[] codes;
	public String value;
	public String source;

	private static String datePattern1 = "yyyy-MM-dd HH:mm:ss.SSS Z";
	private static String datePattern2 = "yyyy-MM-dd HH:mm:ss";
	private static String datePattern3 = "yyyy-MM-dd";
	private static String datePattern4 = "HH:mm:ss";
	private static String datePattern5 = "yyyy-MM-dd HH:mm:ss.SSS";
	private static String datePattern6 = "yyyy-MM";
	private static String datePattern7 = "HH:mm";
	private static String datePattern8 = "HH:mm:ss.SSS";
	private static String datePattern9 = "yyyy-MM-dd HH:mm";
	private static String datePattern10 = "yyyy/MM/dd HH:mm:ss.SSS Z";
	private static String datePattern11 = "yyyy/MM/dd HH:mm:ss";
	private static String datePattern12 = "yyyy/MM/dd";
	private static String datePattern13 = "yyyy/MM/dd HH:mm:ss.SSS";
	private static String datePattern14 = "yyyy/MM";
	private static String datePattern15 = "yyyy/MM/dd HH:mm";
	private static String datePattern16 = "yyyy-MM-ddTHH:mm:ss";

	boolean run = true;

	public static CacheMap.Ccc<String, Pattern> regexCache = new CacheMap.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public static Var build() {
		Var var = new Var();
		return var;
	}

	public static Var build(String code, HttpServletRequest from) {
		return Var.build().code(code).value(from);
	}

	public static Var build(String code, MMap from) {
		return Var.build().code(code).value(from);
	}

	public static Var build(String code, Map from) {
		return Var.build().code(code).value(from);
	}

	public static Var build(String name, String code, HttpServletRequest from) {
		return Var.build().name(name).code(code).value(from);
	}

	public static Var build(String name, String code, MMap from) {
		return Var.build().name(name).code(code).value(from);
	}

	public static Var build(String name, String code, Map from) {
		return Var.build().name(name).code(code).value(from);
	}

	public static Var build(Object... from) {
		return Var.build().value(from);
	}

	public Var name(String name) {
		this.name = name;
		return this;
	}

	public Var code(String... codes) {
		this.codes = codes;
		if (this.codes != null && this.codes.length > 0)
			this.code = this.codes[0];
		if (this.name == null || this.name.isEmpty())
			this.name = this.code;
		return this;
	}

	public Var value(HttpServletRequest from) {
		String value = null;
		if (this.codes != null)
			for (String code : this.codes) {
				value = from.getParameter(code);
				if (value != null) {
					break;
				}
			}
		return value(value);
	}

	public Var value(MMap from) {
		String value = null;
		if (codes != null)
			for (String code : codes) {
				value = from.getString(code);
				if (value != null) {
					value = value;
					break;
				}
			}
		return value(value);

	}

	public Var value(Map from) {
		String value = null;
		if (codes != null)
			for (String code : codes) {
				value = (String) from.get(code);
				if (value != null) {
					break;
				}
			}
		return value(value);
	}

	public Var value(Object... from) {
		if (from != null)
			for (Object value : from) {
				if (value != null) {
					if (value instanceof Float || value instanceof Double) {
						this.value = value.toString().replaceAll("\\.0*$", "");
					} else {
						this.value = value.toString();
					}
					break;
				}
			}

		if (this.value != null && !this.value.isEmpty() && this.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		if ("null".equals(this.value) || "undefined".equals(this.value) || "NaN".equals(this.value))
			this.value = null;
		return this;
	}

	public Var suffix(Object suffix) {
		if (!this.run)
			return this;

		if (value != null)
			value = value + suffix.toString();
		return this;
	}

	public Var prefix(Object prefix) {
		if (!this.run)
			return this;

		if (value != null)
			value = prefix.toString() + value;
		return this;
	}

	public Var trim() {
		if (!this.run)
			return this;

		value = value == null ? null : value.trim();
		return this;
	}

	public Var trimToNull() {
		if (!this.run)
			return this;

		if (value != null && value.trim().isEmpty())
			value = null;
		return this;
	}

	public Var trimToBlank() {
		if (!this.run)
			return this;

		if (value == null)
			value = "";
		value = value.trim();
		return this;
	}

	public Var trimLeft() {
		if (!this.run)
			return this;

		value = value == null ? null : value.trim();
		return this;
	}

	public Var trimRight() {
		if (!this.run)
			return this;

		value = value == null ? null : value.trim();
		return this;
	}

	public Var nullDefault(Object defaultValue) {
		if (!this.run)
			return this;

		String ss = null;
		ss = defaultValue == null ? null : defaultValue.toString().trim();
		if (isNull() && ss != null)
			value = ss;
		return this;
	}

	public Var nullDefault(boolean run, Object defaultValue) {
		if (!this.run)
			return this;

		if (run)
			nullDefault(defaultValue);
		return this;
	}

	public Var blankDefault(Object defaultValue) {
		if (!this.run)
			return this;

		String ss = null;
		ss = defaultValue == null ? null : defaultValue.toString().trim();
		if (isBlank() && ss != null)
			value = ss;
		return this;
	}

	public Var blankDefault(boolean run, Object defaultValue) {
		if (!this.run)
			return this;

		if (run)
			blankDefault(defaultValue);
		return this;
	}

	public Var emptyDefault(Object defaultValue) {
		if (!this.run)
			return this;

		String ss = null;
		ss = defaultValue == null ? null : defaultValue.toString().trim();
		if (isEmpty() && ss != null)
			value = ss;
		return this;
	}

	public Var emptyDefault(boolean run, Object defaultValue) {
		if (!this.run)
			return this;

		if (run)
			emptyDefault(defaultValue);
		return this;
	}

	public Var default1(Object defaultValue) {
		return emptyDefault(defaultValue);
	}

	public Var default1(boolean run, Object defaultValue) {
		return emptyDefault(run, defaultValue);
	}

	public Var stop() {
		this.run = false;
		return this;
	}

	public Var stop(boolean run) {
		if (run)
			this.run = false;
		return this;
	}

	public Var vNull() {
		if (!this.run)
			return this;
		if (this.value == null)
			throw Result.build(8, "\"" + this.name + "\"不能空").errorParam(this.code);
		return this;
	}

	public Var vNull(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vNull();
		return this;
	}

	public Var vEmpty(String msg) {
		vEmpty(true, msg);
		return this;
	}

	public Var vEmpty() {
		vEmpty(true, null);
		return this;
	}

	public static void vAnyExisting(Var... vars) {
		boolean anyExisting = false;
		String names = "";
		for (int i = 0; i < vars.length; i++) {
			Var var = vars[i];
			names += "," + (Stringuu.isEmpty(var.name) ? var.code : var.name);
			if (var.isExisting()) {
				anyExisting = true;
			}
		}
		names = names.length() > 0 ? names.substring(1) : names;
		if (!anyExisting)
			throw Result.build(8, names + ", 至少一个必输");
	}

	public Var vEmpty(boolean run, String msg) {
		if (!this.run)
			return this;
		if (msg == null)
			msg = "\"" + this.name + "\"不能空";
		if (run && (this.value == null || this.value.isEmpty()))
			throw Result.build(8, msg).errorParam(this.code);
		return this;
	}

	public Var vEmpty(boolean run) {
		vEmpty(run, null);
		return this;
	}

	public Var vBlank() {
		if (!this.run)
			return this;
		if ((this.value != null && this.value.isEmpty()))
			throw Result.build(8, "\"" + this.name + "\"不能空").errorParam(this.code);
		return this;
	}

	public Var vBlank(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vBlank();
		return this;
	}

	public Var vLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() != length) {
			throw Result.build(8, "\"" + this.name + "\"长度只能是" + length).errorParam(this.code);
		}
		return this;
	}

	public Var vLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vLen(length);
		return this;
	}

	public Var vMinLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() < length) {
			throw Result.build(8, "\"" + this.name + "\"当前长度" + this.value.length() + ", 最小" + length + ".")
					.errorParam(this.code);
		}
		return this;
	}

	public Var vMinLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vMinLen(length);
		return this;
	}

	public Var vMaxLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() > length) {
			throw Result.build(8, "\"" + this.name + "\"当前长度" + this.value.length() + ", 最大" + length + ".")
					.errorParam(this.code);
		}
		return this;
	}

	public Var vMaxLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vMaxLen(length);
		return this;
	}

	public Var vMaxNum(float maxnum) {
		if (!this.run)
			return this;
		if (!isEmpty() && toFloat() > maxnum) {
			throw Result.build(8, "\"" + this.name + "\"最大值" + maxnum).errorParam(this.code);
		}
		return this;
	}

	public Var vMaxNum(boolean run, float maxnum) {
		if (!this.run)
			return this;
		if (run)
			this.vMaxNum(maxnum);
		return this;
	}

	public Var vMinNum(float minnum) {
		if (!this.run)
			return this;
		if (!isEmpty() && toFloat() < minnum) {
			throw Result.build(8, "\"" + this.name + "\"最小值" + minnum).errorParam(this.code);
		}
		return this;
	}

	public Var vMinNum(boolean run, float minnum) {
		if (!this.run)
			return this;
		if (run)
			this.vMinNum(minnum);
		return this;
	}

	public Var vMaxCount(int count) {
		if (!this.run)
			return this;
		if (!this.isEmpty() && this.split().size() > count) {
			throw Result.build(8, "\"" + this.name + "\"最多" + count + "个").errorParam(this.code);
		}
		return this;
	}

	public Var vList() {
		if (!this.run)
			return this;
		LList list = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				list = Var.toList(this.value);
			} catch (Exception e) {

			}
			if (list == null)
				throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		}
		return this;
	}

	public Var vMap() {
		if (!this.run)
			return this;
		MMap list = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				list = Var.toMap(this.value);
			} catch (Exception e) {

			}
			if (list == null)
				throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		}
		return this;
	}

	public Var vInteger() {
		if (!this.run)
			return this;
		Integer integerValue = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				integerValue = Integer.parseInt(this.value);
			} catch (Exception e) {

			}
			if (integerValue == null)
				throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		}
		return this;
	}

	public Var vBoolean() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && !"1".equals(this.value) && !"0".equals(this.value)) {
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		}
		return this;
	}

	public Var vEnum(Object... enums) {
		if (!this.run)
			return this;
		if (this.value == null || this.value.isEmpty() || enums == null || enums.length == 0)
			return this;
		boolean v = Stringuu.equalsAny(this.value, enums);
		if (!v)
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		else
			return this;
	}

	public Var vLong() {
		if (!this.run)
			return this;
		Long longValue = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				longValue = Long.parseLong(this.value);
			} catch (Exception e) {

			}
			if (longValue == null)
				throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		}
		return this;
	}

	public Var vDouble() {
		if (!this.run)
			return this;
		Double doubleValue = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				doubleValue = Double.parseDouble(this.value);
			} catch (Exception e) {

			}
			if (doubleValue == null)
				throw Result.build(8, "\"" + this.name + "\"只能输入数字").errorParam(this.code);
		}
		return this;
	}

	public Var vFloat() {
		if (!this.run)
			return this;
		Float floatValue = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				floatValue = Float.parseFloat(this.value);
			} catch (Exception e) {

			}
			if (floatValue == null)
				throw Result.build(8, "\"" + this.name + "\"只能输入数字").errorParam(this.code);
		}
		return this;
	}

	public Var vDecimal() {
		if (!this.run)
			return this;
		BigDecimal decimalValue = null;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				decimalValue = new BigDecimal(this.value);
			} catch (Exception e) {

			}
			if (decimalValue == null)
				throw Result.build(8, "\"" + this.name + "\"只能输入数字").errorParam(this.code);
		}
		return this;
	}

	public boolean isExisting() {
		return notEmpty();
	}

	public boolean notEmpty() {
		return !isEmpty();
	}

	public boolean sourceIsExisting() {
		return !isEmpty();
	}

	public boolean isEmpty() {
		if (this.value == null || this.value.isEmpty())
			return true;
		return false;
	}

	public boolean notNull() {
		return !isNull();
	}

	public boolean isNull() {
		if (this.value == null)
			return true;
		return false;
	}

	public boolean notBlank() {
		return !isBlank();
	}

	public boolean isBlank() {
		if (this.value != null && this.value.isEmpty())
			return true;
		return false;
	}

	public Var vLenRange(int min, int max) {
		if (!this.run)
			return this;
		vMinLen(min);
		vMaxLen(max);
		return this;
	}

	public Var vNumRange(float min, float max) {
		if (!this.run)
			return this;
		vMinNum(min);
		vMaxNum(max);
		return this;
	}

	public Var vReg(Pattern regex) {
		if (!this.run)
			return this;
		return vReg(regex, null);
	}

	public Var vReg(Pattern regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (regex.matcher(this.value).matches())
				throw Result
						.build(8,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.errorParam(this.code);
		}
		return this;
	}

	public Var vRegNot(Pattern regex) {
		if (!this.run)
			return this;
		return vRegNot(regex, null);
	}

	public Var vRegNot(Pattern regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (!regex.matcher(this.value).matches())
				throw Result
						.build(8,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.errorParam(this.code);
		}
		return this;
	}

	public Var vReg(String regex) {
		if (!this.run)
			return this;
		return vReg(regex, null);
	}

	public Var vReg(String regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw Result
						.build(8,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.errorParam(this.code);
		}
		return this;
	}

	public Var vRegNot(String regex) {
		if (!this.run)
			return this;
		return vRegNot(regex, null);
	}

	public Var vRegNot(String regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw Result
						.build(8,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.errorParam(this.code);
		}
		return this;
	}

	public Var vDate() {
		if (this.value == null || this.value.isEmpty())
			return this;
		Date date = toDate();
		if (date == null)
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		return this;
	}
//	public Value vReplace(String regex, String replace) {
//		if (!this.run)
//			return this;
//		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty() && replace != null)
//			this.value = regexCache.getWithCreate(regex).matcher(this.value).replaceAll(replace);
//		return this;
//	}
//
//	public Value vReplace(String regex, String replace, boolean run) {
//		if (!this.run)
//			return this;
//		if (run)
//			return vReplace(regex, replace);
//		return this;
//	}

	public LList split() {
		return Stringuu.split(this.value, ",");
	}

	public LList split(String separator) {
		return Stringuu.split(this.value, separator);
	}

	@Override
	public String toString() {
		return this.value;
	}

	public MMap toMap() {
		return Var.toMap(value);
	}

	public LList toList() {
		return Var.toList(value);
	}

	public Integer toInteger() {
		return isEmpty() ? null : toInteger(value);
	}

	public Float toFloat() {
		return isEmpty() ? null : toFloat(value);
	}

	public Long toLong() {
		return isEmpty() ? null : toLong(value);
	}

	public BigDecimal toDecimal() {
		return isEmpty() ? null : toDecimal(value);
	}

	public String formatDate(String pattern) {
		Date data = toDate();
		return data == null ? null : new SimpleDateFormat(pattern).format(data);
	}

	public Date toDate() {
		return isEmpty() ? null : toDate(this.value);
	}

	public void throw1(String message) {
		throw Result.build(8, "\"" + this.name + "\"" + message).errorParam(this.code);
	}

	public void throw1(boolean run, String message) {
		if (run)
			throw Result.build(8, "\"" + this.name + "\"" + message).errorParam(this.code);
	}

	public boolean equalsIgnoreCase(Object object) {
		if (this.value == object)
			return true;
		if (this.value == null || object == null)
			return false;
		return this.value.equalsIgnoreCase(object.toString());
	}

	public boolean notEqualsIgnoreCase(Object object) {
		return !equalsIgnoreCase(object);
	}

	public boolean equals(Object object) {
		if (this.value == object)
			return true;
		if (this.value == null || object == null)
			return false;
		return this.value.equals(object.toString());
	}

	public boolean notEquals(Object object) {
		return !equals(object);
	}

	public Var toLowerCase() {
		if (!this.run)
			return this;

		if (value != null)
			value = value.toLowerCase();
		return this;
	}

	public Var toUpperCase() {
		if (!this.run)
			return this;

		if (value != null)
			value = value.toUpperCase();
		return this;
	}

	public Var replaceAll(String regex, Object replacement) {
		if (!this.run)
			return this;

		if (value != null)
			value = value.replaceAll(regex, replacement.toString());
		return this;
	}

	public Var substring(int beginIndex, int endIndex) {
		if (!this.run)
			return this;

		if (value != null)
			value = value.substring(beginIndex, endIndex);
		return this;
	}

	public Var substring(int beginIndex) {
		if (!this.run)
			return this;

		if (value != null)
			value = value.substring(beginIndex);
		return this;
	}

	public boolean contains(Object prefix) {
		if (this.value != null)
			return this.value.contains(prefix.toString());
		return false;
	}

	public boolean startsWith(Object prefix) {
		if (this.value != null)
			return this.value.startsWith(prefix.toString());
		return false;
	}

	public boolean endsWith(Object suffix) {
		if (this.value != null)
			return this.value.endsWith(suffix.toString());
		return false;
	}

	public static Boolean toBoolean(Object value) {
		try {
			if (value == null)
				return null;
			return Boolean.parseBoolean(value.toString());
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
		return null;

	}

	public static Integer toInteger(Object value) {
		try {
			if (value == null)
				return null;
			if (value instanceof Integer)
				return (Integer) value;
			if (value instanceof Boolean)
				return (Boolean) value ? 1 : 0;
			return new Integer(value.toString().split("\\.")[0]);
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
		}
		return null;

	}

	public static Float toFloat(Object value) {
		try {
			if (value == null)
				return null;
			if (value instanceof Float)
				return (Float) value;
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return null;
			else
				return new Float(valueStr);
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	public static Double toDouble(Object value) {
		try {
			if (value == null)
				return null;
			if (value instanceof Float)
				return (Double) value;
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return null;
			else
				return new Double(valueStr);
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	public static String toString(Object value) {
		if (value == null)
			return null;
		if (value instanceof String)
			return (String) value;
//		if (value instanceof Double) {
//			String dds = BigDecimal.valueOf((Double) value).toPlainString();
//			String[] ss = dds.split("\\.");
//			String d = null;
//			String d1 = ss[0];
//			String d2 = null;
//			if (ss.length > 1)
//				d2 = ss[1];
//			if (d2 != null && !d2.isEmpty()) {
//				if (d2.replaceAll("0", "").isEmpty())
//					d2 = null;
//			}
//			d = d1;
//			if (d2 != null && !d2.isEmpty())
//				d = d + "." + d2;
//			return d;
//		}
//		if (value instanceof Float) {
//			String dds = BigDecimal.valueOf(((Float) value).doubleValue()).toPlainString();
//			String[] ss = dds.split("\\.");
//			String d = null;
//			String d1 = ss[0];
//			String d2 = null;
//			if (ss.length > 1)
//				d2 = ss[1];
//			if (d2 != null && !d2.isEmpty()) {
//				if (d2.replaceAll("0", "").isEmpty())
//					d2 = null;
//			}
//			d = d1;
//			if (d2 != null && !d2.isEmpty())
//				d = d + "." + d2;
//			return d;
//		}
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
		if (value instanceof LocalDateTime)
			return Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant());
		try {
			date = new SimpleDateFormat(datePattern1).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern2).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}

		try {
			date = new SimpleDateFormat(datePattern3).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}

		try {
			date = new SimpleDateFormat(datePattern4).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern5).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern6).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern7).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern8).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern9).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern10).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern11).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern12).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern13).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern14).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern15).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new SimpleDateFormat(datePattern16).parse(value.toString());
			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}
		try {
			date = new Date(Long.parseLong(value.toString()));
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
			return new Long(valueStr.split("\\.")[0]);
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

	public static LList toList(Object value) {
		try {
			if (value == null)
				return LList.build();
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return LList.build();
			else
				return LList.build(JSONObject.parseArray(valueStr));
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			return LList.build();
		}
	}

	public static MMap toMap(Object value) {
		try {
			if (value == null)
				return MMap.build();
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return MMap.build();
			else
				return MMap.build(JSONObject.parseObject(valueStr, LinkedHashMap.class));
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			return MMap.build();
		}
	}

	public static Object attr(Object target, Object... keys)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		for (int i = 0; i < keys.length; i++) {
			if (target instanceof Map) {
				target = ((Map) target).get(keys[i]);
			} else if (target instanceof String) {

			} else if (target instanceof Object) {
				Field f = Object.class.getDeclaredField(keys[i].toString());
				f.setAccessible(true);
				target = f.get(target);
			}

			if (target == null)
				break;
		}
		return target;
	}

	public Var concat(Object str) {
		String str2 = str == null ? "" : str.toString();
		value = value == null ? "" : value;
		value = value.concat(str2);
		return this;
	}

	public Var reset() {
		value = this.source;
		run = true;
		return this;
	}

	public Var clone() {
		Var var2 = new Var();
		var2.name = this.name;
		var2.value = this.value;
		var2.source = this.source;
		var2.code = this.code;
		var2.codes = this.codes;
		return var2;
	}

	public Var regen() {
		Var var2 = new Var();
		var2.name = this.name;
		var2.value = this.value;
		var2.source = this.value;
		var2.code = this.code;
		var2.codes = this.codes;
		return var2;
	}

	public static Var timeId() {
		return Var.build(Stringuu.timeId());
	}

	public static Var timeId(int length) {
		return Var.build(Stringuu.timeId(length));
	}

	public static Var uuid() {
		return Var.build(UUID.randomUUID().toString().replace("-", ""));
	}

	public static Var randomNumeric(final int count) {
		return Var.build(RandomStringUtils.randomNumeric(count));
	}

	public static Var randomAlphabetic(final int count) {
		return Var.build(RandomStringUtils.randomAlphabetic(count));
	}

	public static Var randomAlphanumeric(final int count) {
		return Var.build(RandomStringUtils.randomAlphanumeric(count));
	}

	public static void main(String[] args) throws ParseException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		System.out.println(UUID.randomUUID());
	}
}
