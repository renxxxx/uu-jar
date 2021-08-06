package renx.uu;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Par {
	private static Logger logger = LoggerFactory.getLogger(Par.class);

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
	public Integer vvinteger;
	public Float vvfloat;
	public Double vvdouble;
	public Long vvlong;
	public BigDecimal vvdecimal;
	public Date vvdate;
	public String[] vvstrings;
	public String[] vvenums = null;
	public static Cachem.Ccc<String, Pattern> regexCache = new Cachem.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public Par clear() {
		this.vvstrings = null;
		this.vvdate = null;
		this.vvinteger = null;
		this.vvfloat = null;
		this.vvlong = null;
		this.vvdecimal = null;
		this.vvenums = null;
		return this;
	}

	public static Par go(String name, String code, String... values) {
		Par var = new Par();
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
			throw Res.go(1001, "\"" + var.name + "\"有误").setErrParam(var.code);
		if ("null".equals(var.value) || "undefined".equals(var.value))
			var.value = null;
		return var;
	}

	public static Par go2(String... values) {
		Par var = new Par();
		if (values != null)
			for (String value : values) {
				if (value != null) {
					var.value = value;
					break;
				}
			}

		if (var.value != null && !var.value.isEmpty() && var.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T).*"))
			throw Res.go(1001, "\"" + var.name + "\"有误").setErrParam(var.code);
		if ("null".equals(var.value) || "undefined".equals(var.value))
			var.value = null;

		return var;
	}

	public Par suffix(String suffix) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value + suffix;
		return this;
	}

	public Par prefix(String prefix) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = prefix + this.value;
		return this;
	}

	public Par trim() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Par trimToNull() {
		if (!this.run)
			return this;
		if (this.value != null && this.value.trim().isEmpty())
			this.value = null;
		return this;
	}

	public Par trimToBlank() {
		if (!this.run)
			return this;
		if (this.value == null)
			this.value = "";
		this.value = this.value.trim();
		return this;
	}

	public Par trimLeft() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Par trimRight() {
		if (!this.run)
			return this;
		this.value = this.value == null ? null : this.value.trim();
		return this;
	}

	public Par setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public Par nullDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isNull() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Par nullDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.nullDef(defaultValue);
		return this;
	}

	public Par blankDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isBlank() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Par blankDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.blankDef(defaultValue);
		return this;
	}

	public Par emptyDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isEmpty() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Par emptyDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.emptyDef(defaultValue);
		return this;
	}

	public Par stop() {
		this.run = false;
		return this;
	}

	public Par stop(boolean run) {
		if (run)
			this.run = false;
		return this;
	}

	public Par vNull() {
		if (!this.run)
			return this;
		if (this.value == null)
			throw Res.go(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Par vNull(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vNull();
		return this;
	}

	public Par vEmpty(String msg) {
		vEmpty(true, msg);
		return this;
	}

	public Par vEmpty() {
		vEmpty(true, null);
		return this;
	}

	public Par vEmpty(boolean run, String msg) {
		if (!this.run)
			return this;
		if (msg == null)
			msg = "\"" + this.name + "\"不能空";
		if (run && (this.value == null || this.value.isEmpty()))
			throw Res.go(1001, msg).setErrParam(this.code);
		return this;
	}

	public Par vEmpty(boolean run) {
		vEmpty(run, null);
		return this;
	}

	public Par vBlank() {
		if (!this.run)
			return this;
		if ((this.value != null && this.value.isEmpty()))
			throw Res.go(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Par vBlank(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vBlank();
		return this;
	}

	public Par vLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() != length) {
			throw Res.go(1001, "\"" + this.name + "\"长度只能是" + length).setErrParam(this.code);
		}
		return this;
	}

	public Par vLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vLen(length);
		return this;
	}

	public Par vMinLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() < length) {
			throw Res.go(1001, "\"" + this.name + "\"长度最低" + length).setErrParam(this.code);
		}
		return this;
	}

	public Par vMinLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vMinLen(length);
		return this;
	}

	public Par vMaxLen(int length) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && length > -1 && this.value.length() > length) {
			throw Res.go(1001, "\"" + this.name + "\"长度最大" + length).setErrParam(this.code);
		}
		return this;
	}

	public Par vMaxLen(boolean run, int length) {
		if (!this.run)
			return this;
		if (run)
			this.vMaxLen(length);
		return this;
	}

	public Par vMaxNum(float maxnum) {
		if (!this.run)
			return this;
		if (!isEmpty() && toFloat() > maxnum) {
			throw Res.go(1001, "\"" + this.name + "\"最大" + maxnum).setErrParam(this.code);
		}
		return this;
	}

	public Par vMaxNum(boolean run, float maxnum) {
		if (!this.run)
			return this;
		if (run)
			this.vMaxNum(maxnum);
		return this;
	}

	public Par vMinNum(float minnum) {
		if (!this.run)
			return this;
		if (!isEmpty() && toFloat() < minnum) {
			throw Res.go(1001, "\"" + this.name + "\"最小" + minnum).setErrParam(this.code);
		}
		return this;
	}

	public Par vMinNum(boolean run, float minnum) {
		if (!this.run)
			return this;
		if (run)
			this.vMinNum(minnum);
		return this;
	}

	public Par vMaxCount(int count) {
		if (!this.run)
			return this;
		if (!this.isEmpty() && this.toStrings() != null && this.toStrings().length > count) {
			throw Res.go(1001, "\"" + this.name + "\"最多" + count + "个").setErrParam(this.code);
		}
		return this;
	}

	public Par vInteger() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.vvinteger = Integer.parseInt(this.value);
			} catch (Exception e) {

			}
			if (this.vvinteger == null)
				throw Res.go(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Par vBoolean() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && !"1".equals(this.value) && !"0".equals(this.value)) {
			throw Res.go(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Par vEnum(String[] values, String error) {
		if (!this.run)
			return this;
		if (isEmpty())
			return this;
		this.vvenums = values;
		boolean v = Stringuu.equalsAny(this.value, values);
		if (!v)
			throw Res.go(1001, "\"" + this.name + "\"有误").setErrParam(this.code).setError(error);
		else
			return this;
	}

	public Par vEnum(String... values) {
		return vEnum(values, null);
	}

	public Par vLong() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.vvlong = Long.parseLong(this.value);
			} catch (Exception e) {

			}
			if (this.vvlong == null)
				throw Res.go(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Par vDouble() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.vvdouble = Double.parseDouble(this.value);
			} catch (Exception e) {

			}
			if (this.vvdouble == null)
				throw Res.go(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
	}

	public Par vFloat() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.vvfloat = Float.parseFloat(this.value);
			} catch (Exception e) {

			}
			if (this.vvfloat == null)
				throw Res.go(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
		}
		return this;
	}

	public Par vDecimal() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty()) {
			try {
				this.vvdecimal = new BigDecimal(this.value);
			} catch (Exception e) {

			}
			if (this.vvdecimal == null)
				throw Res.go(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
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

	public Par vLenRange(int min, int max) {
		if (!this.run)
			return this;
		vMinLen(min);
		vMaxLen(max);
		return this;
	}

	public Par vNumRange(float min, float max) {
		if (!this.run)
			return this;
		vMinNum(min);
		vMaxNum(max);
		return this;
	}

	public Par vReg(Pattern regex) {
		if (!this.run)
			return this;
		return vReg(regex, null);
	}

	public Par vReg(Pattern regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (!regex.matcher(this.value).matches())
				throw Res
						.go(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Par vRegNot(Pattern regex) {
		if (!this.run)
			return this;
		return vRegNot(regex, null);
	}

	public Par vRegNot(Pattern regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null) {
			if (regex.matcher(this.value).matches())
				throw Res
						.go(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Par vReg(String regex) {
		if (!this.run)
			return this;
		return vReg(regex, null);
	}

	public Par vReg(String regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (!regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw Res
						.go(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Par vRegNot(String regex) {
		if (!this.run)
			return this;
		return vRegNot(regex, null);
	}

	public Par vRegNot(String regex, String note) {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && regex != null && !regex.isEmpty()) {
			if (regexCache.getWithCreate(regex).matcher(this.value).matches())
				throw Res
						.go(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Par vDate() {
		if (this.value == null || this.value.isEmpty())
			return this;
		toDate();
		if (this.vvdate == null)
			throw Res.go(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		else {
			this.value = new SimpleDateFormat(datePattern1).format(this.vvdate);
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
		if (this.vvstrings != null)
			return this.vvstrings;
		this.vvstrings = isNull() ? null
				: StringUtils.splitByWholeSeparatorPreserveAllTokens(this.value, this.separator);
		return this.vvstrings;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public Integer toInteger() {
		if (this.vvinteger != null)
			return this.vvinteger;
		this.vvinteger = isEmpty() ? null : Integer.parseInt(this.value.split("\\.")[0]);
		return this.vvinteger;
	}

	public Float toFloat() {
		if (this.vvfloat != null)
			return this.vvfloat;
		this.vvfloat = isEmpty() ? null : Float.parseFloat(this.value);
		return this.vvfloat;
	}

	public Long toLong() {
		if (this.vvlong != null)
			return this.vvlong;
		this.vvlong = isEmpty() ? null : Long.parseLong(this.value);
		return this.vvlong;
	}

	public BigDecimal toDecimal() {
		if (this.vvdecimal != null)
			return this.vvdecimal;
		this.vvdecimal = isEmpty() ? null : new BigDecimal(this.value);
		return this.vvdecimal;
	}

	public String formatDate(String pattern) {
		Date data = this.toDate();
		return new SimpleDateFormat(pattern).format(data);
	}

	public Date toDate() {
		if (this.vvdate != null)
			return this.vvdate;
		this.vvdate = isEmpty() ? null : toDate(this.value);
		return this.vvdate;
	}

	public void bomb(String message) {
		throw Res.go(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public void bomb(boolean run, String message) {
		if (run)
			throw Res.go(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public boolean equals(Object object) {
		if (this.value == object)
			return true;
		if (this.value == null && object != null)
			return false;
		return this.value.equals(object);
	}

	public Par toLowerCase() {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.toLowerCase();
		return this;
	}

	public Par toUpperCase() {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.toUpperCase();
		return this;
	}

	public Par replaceAll(String regex, String replacement) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.replaceAll(regex, replacement);
		return this;
	}

	public Par substring(int beginIndex, int endIndex) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex, endIndex);
		return this;
	}

	public Par substring(int beginIndex) {
		if (!this.run)
			return this;
		if (this.value != null)
			this.value = this.value.substring(beginIndex);
		return this;
	}

	public Par concat(String str) {
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

	public Par set(String value) {
		clear();
		this.value = value;
		return this;
	}

	public static Integer toInteger(Object value) {
		if (value == null)
			return null;
		if (value instanceof Integer)
			return (Integer) value;
		if (value instanceof Boolean)
			return (Boolean) value ? 1 : 0;
		try {
			return new Integer(value.toString().split("\\.")[0]);
		} catch (Exception e) {
		}
		return null;

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

	public static Double toDouble(Object value) {
		if (value == null)
			return null;
		if (value instanceof Float)
			return (Double) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Double(valueStr);
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

	public static JSONArray toJsonArr(Object value) {
		try {
			if (value == null)
				return null;
			if (value instanceof JSONArray)
				return (JSONArray) value;
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return null;
			else
				return JSON.parseArray(valueStr);
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	public static JSONObject toJson(Object value) {
		try {
			if (value == null)
				return null;
			if (value instanceof JSONObject)
				return (JSONObject) value;
			String valueStr = value.toString();
			if (valueStr.trim().isEmpty())
				return null;
			else
				return JSONObject.parseObject(valueStr);
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			return null;
		}
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

	public static void main(String[] args) throws ParseException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Par value = Par.go(null, null, "20200506132311");
		System.out.println(new SimpleDateFormat("yyyy-MM").parse("2020-03"));
	}
}
