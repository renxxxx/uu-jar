package renx.uu;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Paramm {
	private static Logger logger = LoggerFactory.getLogger(Paramm.class);

	public String name;
	public String code;
	public String value;

	public String separator = ",";

	public static String datePattern = "yyyy-MM-dd HH:mm:ss.SSS Z";
	public static String datePattern1 = "yyyy-MM-dd HH:mm:ss";
	public static String datePattern2 = "yyyy-MM-dd";
	public static String datePattern3 = "HH:mm:ss";
	public static String datePattern4 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static String datePattern5 = "yyyy-MM";
	public static String datePattern6 = "HH:mm";
	public static String datePattern7 = "HH:mm:ss.SSS";
	public static String datePattern8 = "yyyy-MM-dd HH:mm";

	boolean run = true;

	public Integer integerv;
	public Float floatv;
	public Double doublev;
	public Long longv;
	public BigDecimal decimalv;
	public Date datev;
	public String[] stringsv;

	public String[] enums = null;

	public static Cachem.Ccc<String, Pattern> regexCache = new Cachem.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public Paramm reset() {
		this.stringsv = null;
		this.datev = null;
		this.integerv = null;
		this.floatv = null;
		this.longv = null;
		this.decimalv = null;
		this.enums = null;
		return this;
	}

	public static Paramm build() {
		Paramm var = new Paramm();
		return var;
	}

	public Paramm name(String name) {
		this.name = name;
		return this;
	}

	public Paramm code(String code) {
		this.code = code;
		if (this.name == null || this.name.isEmpty())
			this.name = code;
		return this;
	}

	public Paramm value(HttpServletRequest from) {
		this.value = from.getParameter(code);
		return this;
	}

	public Paramm value(MMap from) {
		this.value = from.getString(code);
		return this;
	}

	public Paramm value(String... froms) {
		if (froms != null)
			for (String value : froms) {
				if (value != null) {
					this.value = value;
					break;
				}
			}

		if (this.value != null && !this.value.isEmpty() && this.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw RRes.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		if ("null".equals(this.value) || "undefined".equals(this.value))
			this.value = null;
		return this;
	}

	@Deprecated
	public static Paramm go(String name, String code, String... values) {
		Paramm var = new Paramm();
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
			throw RRes.build(1001, "\"" + var.name + "\"有误").setErrParam(var.code);
		if ("null".equals(var.value) || "undefined".equals(var.value))
			var.value = null;
		return var;
	}

	@Deprecated
	public static Paramm go2(String... values) {
		Paramm var = new Paramm();
		if (values != null)
			for (String value : values) {
				if (value != null) {
					var.value = value;
					break;
				}
			}

		if (var.value != null && !var.value.isEmpty() && var.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw RRes.build(1001, "\"" + var.name + "\"有误").setErrParam(var.code);
		if ("null".equals(var.value) || "undefined".equals(var.value))
			var.value = null;

		return var;
	}

	public Paramm suffix(String suffix) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value + suffix;
		return this;
	}

	public Paramm prefix(String prefix) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = prefix + this.value;
		return this;
	}

	public Paramm trim() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Paramm trimToNull() {
		if (!this.run)
			return this;
		if (this.value != null && this.value.trim().isEmpty())
			this.value = null;
		return this;
	}

	public Paramm trimToBlank() {
		if (!this.run)
			return this;
		if (this.value == null)
			this.value = "";
		this.value = this.value.trim();
		return this;
	}

	public Paramm trimLeft() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Paramm trimRight() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Paramm setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public Paramm nullDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isNull() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Paramm nullDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.nullDef(defaultValue);
		return this;
	}

	public Paramm blankDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isBlank() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Paramm blankDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.blankDef(defaultValue);
		return this;
	}

	public Paramm emptyDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isEmpty() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Paramm emptyDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.emptyDef(defaultValue);
		return this;
	}

	public Paramm stop() {
		this.run = false;
		return this;
	}

	public Paramm stop(boolean run) {
		if (run)
			this.run = false;
		return this;
	}

	public Paramm vNull() {
		if (!this.run)
			return this;
		if (this.value == null)
			throw RRes.build(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Paramm vNull(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vNull();
		return this;
	}

	public Paramm vEmpty(String msg) {
		vEmpty(true, msg);
		return this;
	}

	public Paramm vEmpty() {
		vEmpty(true, null);
		return this;
	}

	public Paramm vEmpty(boolean run, String msg) {
		if (!this.run)
			return this;
		if (msg == null)
			msg = "\"" + this.name + "\"不能空";
		if (run && (this.value == null || this.value.isEmpty()))
			throw RRes.build(1001, msg).setErrParam(this.code);
		return this;
	}

	public Paramm vEmpty(boolean run) {
		vEmpty(run, null);
		return this;
	}

	public Paramm vBlank() {
		if (!this.run)
			return this;
		if ((this.value != null && this.value.isEmpty()))
			throw RRes.build(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Paramm vBlank(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vBlank();
		return this;
	}

	public Paramm vLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() != length) {
			throw RRes.build(1001, "\"" + this.name + "\"长度只能是" + length).setErrParam(this.code);
		}
		return this;
	}

	public Paramm vLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vLen(length);
		return this;
	}

	public Paramm vMinLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() < length) {
			throw RRes.build(1001, "\"" + this.name + "\"长度最低" + length).setErrParam(this.code);
		}
		return this;
	}

	public Paramm vMinLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vMinLen(length);
		return this;
	}

	public Paramm vMaxLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() > length) {
			throw RRes.build(1001, "\"" + this.name + "\"长度最大" + length).setErrParam(this.code);
		}
		return this;
	}

	public Paramm vMaxLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vMaxLen(length);
		return this;
	}

	public Paramm vMaxNum(float maxnum) {
		if (!this.run)
			return this;
		if (!isEmpty() && toFloat() > maxnum) {
			throw RRes.build(1001, "\"" + this.name + "\"最大" + maxnum).setErrParam(this.code);
		}
		return this;
	}

	public Paramm vMaxNum(boolean run, float maxnum) {
		if (!this.run)
			return this;
		if (run)
			this.vMaxNum(maxnum);
		return this;
	}

	public Paramm vMinNum(float minnum) {
		if (!this.run)
			return this;
		if (!isEmpty() && toFloat() < minnum) {
			throw RRes.build(1001, "\"" + this.name + "\"最小" + minnum).setErrParam(this.code);
		}
		return this;
	}

	public Paramm vMinNum(boolean run, float minnum) {
		if (!this.run)
			return this;
		if (run)
			this.vMinNum(minnum);
		return this;
	}

	public Paramm vMaxCount(int count) {
		if (!this.run)
			return this;
		if (!this.isEmpty() && this.toStrings() != null && this.toStrings().length > count) {
			throw RRes.build(1001, "\"" + this.name + "\"最多" + count + "个").setErrParam(this.code);
		}
		return this;
	}

	public Paramm vInteger() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.integerv = Integer.parseInt(this.value);
			} catch (Exception e) {

			}
			if (this.integerv == null)
				throw RRes.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Paramm vBoolean() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && !"1".equals(this.value) && !"0".equals(this.value)) {
			throw RRes.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Paramm vEnum(String[] enums, String error) {
		if (!this.run)
			return this;
		if (isEmpty())
			return this;
		this.enums = enums;
		boolean v = Stringuu.equalsAny(this.value, enums);
		if (!v)
			throw RRes.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code).setError(error);
		else
			return this;
	}

	public Paramm vEnum(String... enums) {
		return vEnum(enums, null);
	}

	public Paramm vLong() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.longv = Long.parseLong(this.value);
			} catch (Exception e) {

			}
			if (this.longv == null)
				throw RRes.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Paramm vDouble() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.doublev = Double.parseDouble(this.value);
			} catch (Exception e) {

			}
			if (this.doublev == null)
				throw RRes.build(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
	}

	public Paramm vFloat() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.floatv = Float.parseFloat(this.value);
			} catch (Exception e) {

			}
			if (this.floatv == null)
				throw RRes.build(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
	}

	public Paramm vDecimal() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.decimalv = new BigDecimal(this.value);
			} catch (Exception e) {

			}
			if (this.decimalv == null)
				throw RRes.build(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
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

	public Paramm vLenRange(int min, int max) {
		if (!this.run)
			return this;
		vMinLen(min);
		vMaxLen(max);
		return this;
	}

	public Paramm vNumRange(float min, float max) {
		if (!this.run)
			return this;
		vMinNum(min);
		vMaxNum(max);
		return this;
	}

	public Paramm vReg(Pattern regex) {
		if (!this.run)
			return this;
		return vReg(regex, null);
	}

	public Paramm vReg(Pattern regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (!regex.matcher(this.value).matches())
				throw RRes
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Paramm vRegNot(Pattern regex) {
		if (!this.run)
			return this;
		return vRegNot(regex, null);
	}

	public Paramm vRegNot(Pattern regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (regex.matcher(this.value).matches())
				throw RRes
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Paramm vReg(String regex) {
		if (!this.run)
			return this;
		return vReg(regex, null);
	}

	public Paramm vReg(String regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw RRes
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Paramm vRegNot(String regex) {
		if (!this.run)
			return this;
		return vRegNot(regex, null);
	}

	public Paramm vRegNot(String regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw RRes
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Paramm vDate() {
		if (this.value == null || this.value.isEmpty())
			return this;
		toDate();
		if (this.datev == null)
			throw RRes.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		else {
			this.value = new SimpleDateFormat(datePattern1).format(this.datev);
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
		if (this.stringsv != null)
			return this.stringsv;
		this.stringsv = isNull() ? null
				: StringUtils.splitByWholeSeparatorPreserveAllTokens(this.value, this.separator);
		return this.stringsv;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public Integer toInteger() {
		if (this.integerv != null)
			return this.integerv;
		this.integerv = isEmpty() ? null : Integer.parseInt(this.value.split("\\.")[0]);
		return this.integerv;
	}

	public Float toFloat() {
		if (this.floatv != null)
			return this.floatv;
		this.floatv = isEmpty() ? null : Float.parseFloat(this.value);
		return this.floatv;
	}

	public Long toLong() {
		if (this.longv != null)
			return this.longv;
		this.longv = isEmpty() ? null : Long.parseLong(this.value);
		return this.longv;
	}

	public BigDecimal toDecimal() {
		if (this.decimalv != null)
			return this.decimalv;
		this.decimalv = isEmpty() ? null : new BigDecimal(this.value);
		return this.decimalv;
	}

	public String formatDate(String pattern) {
		Date data = this.toDate();
		return new SimpleDateFormat(pattern).format(data);
	}

	public Date toDate() {
		if (this.datev != null)
			return this.datev;
		this.datev = isEmpty() ? null : toDate(this.value);
		return this.datev;
	}

	public void bomb(String message) {
		throw RRes.build(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public void bomb(boolean run, String message) {
		if (run)
			throw RRes.build(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public boolean equals(Object object) {
		if (this.value == object)
			return true;
		if (this.value == null && object != null)
			return false;
		return this.value.equals(object);
	}

	public Paramm toLowerCase() {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.toLowerCase();
		return this;
	}

	public Paramm toUpperCase() {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.toUpperCase();
		return this;
	}

	public Paramm replaceAll(String regex, String replacement) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.replaceAll(regex, replacement);
		return this;
	}

	public Paramm substring(int beginIndex, int endIndex) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex, endIndex);
		return this;
	}

	public Paramm substring(int beginIndex) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex);
		return this;
	}

	public Paramm concat(String str) {
		if (!this.run)
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

	public Paramm set(String value) {
		reset();
		this.value = value;
		return this;
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

	public static LList toJsonArr(Object value) {
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

	public static void main(String[] args) throws ParseException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Paramm value = Paramm.go(null, null, "20200506132311");
		System.out.println(new SimpleDateFormat("yyyy-MM").parse("2020-03"));
	}
}
