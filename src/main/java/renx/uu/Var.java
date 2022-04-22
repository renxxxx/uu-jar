package renx.uu;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Var {
	private static Logger logger = LoggerFactory.getLogger(Var.class);

	public String name;
	public String code;
	public String[] codes;
	public String value;
	public String sourceValue;

	private String separator = ",";

	private static String datePattern = "yyyy-MM-dd HH:mm:ss.SSS Z";
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

	private Integer integerValue;
	private Float floatValue;
	private Double doubleValue;
	private Long longValue;
	private BigDecimal decimalValue;
	private Date dateValue;
	private String[] stringsValue;

	public String[] verifiedEnums = null;

	public static CacheMap.Ccc<String, Pattern> regexCache = new CacheMap.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public Var reset() {
		this.stringsValue = null;
		this.dateValue = null;
		this.integerValue = null;
		this.floatValue = null;
		this.longValue = null;
		this.decimalValue = null;
		this.verifiedEnums = null;
		this.value = this.sourceValue;
		return this;
	}

	public static Var build() {
		Var var = new Var();
		return var;
	}

	public Var build(HttpServletRequest from) {
		return Var.build().value(from);
	}

	public Var build(MMap from) {
		return Var.build().value(from);
	}

	public Var build(String... from) {
		return Var.build().value(from);
	}

	public Var name(String name) {
		this.name = name;
		return this;
	}

	public Var code(String... codes) {
		this.codes = codes;
		if (codes != null && codes.length > 0)
			this.code = codes[0];
		if (this.name == null || this.name.isEmpty())
			this.name = code;
		return this;
	}

	public Var value(HttpServletRequest from) {
		reset();
		if (this.codes != null)
			for (String code : this.codes) {
				String value = from.getParameter(code);
				if (value != null) {
					this.value = value;
					break;
				}
			}
		if (this.value != null && !this.value.isEmpty() && this.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		if ("null".equals(this.value) || "undefined".equals(this.value) || "NaN".equals(this.value))
			this.value = null;
		this.sourceValue = this.value;
		return this;
	}

	public Var value(MMap from) {
		reset();
		this.value = from.getString(code);
		if (this.value != null && !this.value.isEmpty() && this.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		if ("null".equals(this.value) || "undefined".equals(this.value) || "NaN".equals(this.value))
			this.value = null;
		this.sourceValue = this.value;
		return this;
	}

	public Var value(Object... from) {
		reset();
		if (from != null)
			for (Object value : from) {
				if (value != null) {
					this.value = value.toString();
					break;
				}
			}

		if (this.value != null && !this.value.isEmpty() && this.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		if ("null".equals(this.value) || "undefined".equals(this.value) || "NaN".equals(this.value))
			this.value = null;
		this.sourceValue = this.value;
		return this;
	}

	@Deprecated
	public static Var go(String name, String code, String... values) {
		Var var = new Var();
		var.name = name;
		var.code = code;
		if (values != null)
			for (String value : values) {
				if (value != null) {
					var.value = value;
					break;
				}
			}

		if (var.value != null && !var.value.isEmpty() && var.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Result.build(8, "\"" + var.name + "\"有误").errorParam(var.code);
		if ("null".equals(var.value) || "undefined".equals(var.value))
			var.value = null;
		return var;
	}

	@Deprecated
	public static Var go2(String... values) {
		Var var = new Var();
		if (values != null)
			for (String value : values) {
				if (value != null) {
					var.value = value;
					break;
				}
			}

		if (var.value != null && !var.value.isEmpty() && var.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Result.build(8, "\"" + var.name + "\"有误").errorParam(var.code);
		if ("null".equals(var.value) || "undefined".equals(var.value))
			var.value = null;

		return var;
	}

	public Var suffix(String suffix) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value + suffix;
		return this;
	}

	public Var prefix(String prefix) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = prefix + this.value;
		return this;
	}

	public Var trim() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Var trimToNull() {
		if (!this.run)
			return this;
		if (this.value != null && this.value.trim().isEmpty())
			this.value = null;
		return this;
	}

	public Var trimToBlank() {
		if (!this.run)
			return this;
		if (this.value == null)
			this.value = "";
		this.value = this.value.trim();
		return this;
	}

	public Var trimLeft() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Var trimRight() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Var setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public Var nullDef(Object defaultValue) {
		if (!this.run)
			return this;
		String ss = null;
		ss = defaultValue == null ? null : defaultValue.toString().trim();
		if (isNull() && ss != null)
			this.value = ss;
		return this;
	}

	public Var nullDef(boolean run, Object defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.nullDef(defaultValue);
		return this;
	}

	public Var blankDef(Object defaultValue) {
		if (!this.run)
			return this;
		String ss = null;
		ss = defaultValue == null ? null : defaultValue.toString().trim();
		if (isBlank() && ss != null)
			this.value = ss;
		return this;
	}

	public Var blankDef(boolean run, Object defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.blankDef(defaultValue);
		return this;
	}

	public Var emptyDef(Object defaultValue) {
		if (!this.run)
			return this;
		String ss = null;
		ss = defaultValue == null ? null : defaultValue.toString().trim();
		if (isEmpty() && ss != null)
			this.value = ss;
		return this;
	}

	public Var emptyDef(boolean run, Object defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.emptyDef(defaultValue);
		return this;
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
		if (!this.isEmpty() && this.toStrings() != null && this.toStrings().length > count) {
			throw Result.build(8, "\"" + this.name + "\"最多" + count + "个").errorParam(this.code);
		}
		return this;
	}

	public Var vInteger() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.integerValue = Integer.parseInt(this.value);
			} catch (Exception e) {

			}
			if (this.integerValue == null)
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

	public Var vEnum(String... enums) {
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

	public Var vEnum() {
		return vEnum(this.verifiedEnums);
	}

	public Var setEnum(String... enums) {
		this.verifiedEnums = enums;
		return this;
	}

	public Var vLong() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.longValue = Long.parseLong(this.value);
			} catch (Exception e) {

			}
			if (this.longValue == null)
				throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		}
		return this;
	}

	public Var vDouble() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.doubleValue = Double.parseDouble(this.value);
			} catch (Exception e) {

			}
			if (this.doubleValue == null)
				throw Result.build(8, "\"" + this.name + "\"只能输入数字").errorParam(this.code);
		}
		return this;
	}

	public Var vFloat() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.floatValue = Float.parseFloat(this.value);
			} catch (Exception e) {

			}
			if (this.floatValue == null)
				throw Result.build(8, "\"" + this.name + "\"只能输入数字").errorParam(this.code);
		}
		return this;
	}

	public Var vDecimal() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.decimalValue = new BigDecimal(this.value);
			} catch (Exception e) {

			}
			if (this.decimalValue == null)
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
			if (!regex.matcher(this.value).matches())
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
			if (regex.matcher(this.value).matches())
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
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
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
			if (regexCache.getWithCreate(regex).matcher(this.value).matches())
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
		toDate();
		if (this.dateValue == null)
			throw Result.build(8, "\"" + this.name + "\"有误").errorParam(this.code);
		else {
			this.value = new SimpleDateFormat(datePattern2).format(this.dateValue);
		}
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

	public String[] toStrings() {
		if (this.stringsValue != null)
			return this.stringsValue;
		this.stringsValue = isNull() ? null
				: StringUtils.splitByWholeSeparatorPreserveAllTokens(this.value, this.separator);
		return this.stringsValue;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public Integer toInteger() {
		if (this.integerValue != null)
			return this.integerValue;
		this.integerValue = isEmpty() ? null : Integer.parseInt(this.value.split("\\.")[0]);
		return this.integerValue;
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

	public Date toDate() {
		if (this.dateValue != null)
			return this.dateValue;
		this.dateValue = isEmpty() ? null : toDate(this.value);
		return this.dateValue;
	}

	public void bomb(String message) {
		throw Result.build(8, "\"" + this.name + "\"" + message).errorParam(this.code);
	}

	public void bomb(boolean run, String message) {
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
		if (this.value != null)
			this.value = this.value.toLowerCase();
		return this;
	}

	public Var toUpperCase() {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.toUpperCase();
		return this;
	}

	public Var replaceAll(String regex, String replacement) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.replaceAll(regex, replacement);
		return this;
	}

	public Var substring(int beginIndex, int endIndex) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex, endIndex);
		return this;
	}

	public Var substring(int beginIndex) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex);
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
		if (value instanceof Double) {
			String dds = BigDecimal.valueOf((Double) value).toPlainString();
			String[] ss = dds.split("\\.");
			String d = null;
			String d1 = ss[0];
			String d2 = null;
			if (ss.length > 1)
				d2 = ss[1];
			if (d2 != null && !d2.isEmpty()) {
				if (d2.replaceAll("0", "").isEmpty())
					d2 = null;
			}
			d = d1;
			if (d2 != null && !d2.isEmpty())
				d = d + "." + d2;
			return d;
		}
		if (value instanceof Float) {
			String dds = BigDecimal.valueOf((Float) value).toPlainString();
			String[] ss = dds.split("\\.");
			String d = null;
			String d1 = ss[0];
			String d2 = null;
			if (ss.length > 1)
				d2 = ss[1];
			if (d2 != null && !d2.isEmpty()) {
				if (d2.replaceAll("0", "").isEmpty())
					d2 = null;
			}
			d = d1;
			if (d2 != null && !d2.isEmpty())
				d = d + "." + d2;
			return d;
		}
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
			date = new SimpleDateFormat(datePattern).parse(value.toString());
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
			if (value.toString().length() == 10) {
				date = new Date(Long.parseLong(value.toString() + "000"));
				if (date != null) {
					return date;
				}
			}
		} catch (Exception e) {
		}
		try {
			if (value.toString().length() == 13) {
				date = new Date(Long.parseLong(value.toString()));
				if (date != null) {
					return date;
				}
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

	public static LList toJsonArray(Object value) {
		try {
			if (value == null)
				return LList.build();
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return LList.build();
			else
				return LList.build(JSONObject.parseArray(valueStr, ArrayList.class));
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			return LList.build();
		}
	}

	public static MMap toJson(Object value) {
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

	public Var concat(String str) {
		String str2 = str == null ? "" : str;
		this.value = this.value == null ? "" : this.value;
		this.value = this.value.concat(str2);
		return this;
	}

	public String[] split() {
		return split(",");
	}

	public String[] split(String regex) {
		if (this.value == null)
			return new String[] {};
		if (regex == null)
			return new String[] { this.value };
		String[] strs = this.value.split(regex);
		return strs;
	}

	public static void main(String[] args) throws ParseException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		System.out.println(Var.toString(Float.valueOf(.02f)));
	}
}
