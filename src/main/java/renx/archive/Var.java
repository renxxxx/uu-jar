package renx.archive;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Var {
	private static Logger logger = LoggerFactory.getLogger(Var.class);

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
	public Integer integerValue;
	public Float floatValue;
	public Double doubleValue;
	public Long longValue;
	public BigDecimal decimalValue;
	public Date dateValue;
	public String[] stringValues;
	public String[] enumValues = null;
	public static Cachem.Ccc<String, Pattern> regexCache = new Cachem.Ccc<String, Pattern>() {
		@Override
		public Pattern create(String regex) {
			return Pattern.compile(regex);
		}
	};

	public Var clear() {
		this.stringValues = null;
		this.dateValue = null;
		this.integerValue = null;
		this.floatValue = null;
		this.longValue = null;
		this.decimalValue = null;
		this.enumValues = null;
		return this;
	}

	public static Var build(String name, String code, String... values) {
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

		if (var.value != null && !var.value.isEmpty() && var.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)>.*"))
			throw Res.build(1001, "\"" + var.name + "\"有误").setErrParam(var.code);

		return var;
	}

	public static Var build1(String... values) {
		Var var = new Var();
		if (values != null)
			for (String value : values) {
				if (value != null) {
					var.value = value;
					break;
				}
			}

		if (var.value != null && !var.value.isEmpty() && var.value.matches(".*<(s|S)(c|C)(r|R)(i|I)(p|P)(t|T)>.*"))
			throw Res.build(1001, "\"" + var.name + "\"有误").setErrParam(var.code);

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

	public Var nullDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isNull() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Var nullDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.nullDef(defaultValue);
		return this;
	}

	public Var blankDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isBlank() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Var blankDef(boolean run, String defaultValue) {
		if (!this.run)
			return this;
		if (run)
			this.blankDef(defaultValue);
		return this;
	}

	public Var emptyDef(String defaultValue) {
		if (!this.run)
			return this;
		defaultValue = defaultValue == null ? defaultValue : defaultValue.trim();
		if (isEmpty() && defaultValue != null)
			this.value = defaultValue;
		return this;
	}

	public Var emptyDef(boolean run, String defaultValue) {
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
			throw Res.build(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Var vNull(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vNull();
		return this;
	}

	public Var vEmpty() {
		if (!this.run)
			return this;
		if ((this.value == null || this.value.isEmpty()))
			throw Res.build(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
		return this;
	}

	public Var vEmpty(boolean run) {
		if (!this.run)
			return this;
		if (run)
			this.vEmpty();
		return this;
	}

	public Var vBlank() {
		if (!this.run)
			return this;
		if ((this.value != null && this.value.isEmpty()))
			throw Res.build(1001, "\"" + this.name + "\"不能空").setErrParam(this.code);
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
			throw Res.build(1001, "\"" + this.name + "\"长度只能是" + length).setErrParam(this.code);
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
			throw Res.build(1001, "\"" + this.name + "\"长度最低" + length).setErrParam(this.code);
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
			throw Res.build(1001, "\"" + this.name + "\"长度最大" + length).setErrParam(this.code);
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
			throw Res.build(1001, "\"" + this.name + "\"最大" + maxnum).setErrParam(this.code);
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
			throw Res.build(1001, "\"" + this.name + "\"最小" + minnum).setErrParam(this.code);
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
			throw Res.build(1001, "\"" + this.name + "\"最多" + count + "个").setErrParam(this.code);
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
				throw Res.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Var vBoolean() {
		if (!this.run)
			return this;
		if (this.value != null && !this.value.isEmpty() && !"1".equals(this.value) && !"0".equals(this.value)) {
			throw Res.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		}
		return this;
	}

	public Var vEnum(String... values) {
		if (!this.run)
			return this;
		if (isEmpty())
			return this;
		this.enumValues = values;
		boolean v = renx.archive.Stringuu.equalsAny(this.value, values);
		if (!v)
			throw Res.build(1001, "\"" + this.name + "\"只能传下列值" + Arrays.toString(this.enumValues))
					.setErrParam(this.code);
		else
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
				throw Res.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
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
				throw Res.build(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
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
				throw Res.build(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
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
				throw Res.build(1001, "\"" + this.name + "\"只能输入数字").setErrParam(this.code);
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
				throw Res
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
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
				throw Res
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
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
				throw Res
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
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
				throw Res
						.build(1001,
								"\"" + this.name + "\"有误"
										+ (note == null || note.isEmpty() ? "" : ", 正确格式: " + note + "."))
						.setErrParam(this.code);
		}
		return this;
	}

	public Var vDate() {
		if (this.value == null || this.value.isEmpty())
			return this;
		toDate();
		if (this.dateValue == null)
			throw Res.build(1001, "\"" + this.name + "\"有误").setErrParam(this.code);
		else {
			this.value = new SimpleDateFormat(datePattern1).format(this.dateValue);
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

	public String val() {
		return this.value;
	}

	public String[] toStrings() {
		if (this.stringValues != null)
			return this.stringValues;
		this.stringValues = isNull() ? null
				: StringUtils.splitByWholeSeparatorPreserveAllTokens(this.value, this.separator);
		return this.stringValues;
	}

	@Override
	public String toString() {
		return this.val();
	}

	public Integer toInteger() {
		if (this.integerValue != null)
			return this.integerValue;
		this.integerValue = isEmpty() ? null : Integer.parseInt(this.value);
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
		throw Res.build(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public void bomb(boolean run, String message) {
		if (run)
			throw Res.build(1001, "\"" + this.name + "\"" + message).setErrParam(this.code);
	}

	public boolean equals(Object object) {
		if (this.value == object)
			return true;
		if (this.value == null && object != null)
			return false;
		return this.value.equals(object);
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

	public Var concat(String str) {
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

	public Var set(String value) {
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

	public static JSONObject toJson(Object value) {
		if (value == null)
			return null;
		if (value instanceof JSONObject)
			return (JSONObject) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return JSONObject.parseObject(valueStr);
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
		Var value = Var.build(null, null, "20200506132311");
		System.out.println(new SimpleDateFormat("yyyy-MM").parse("2020-03"));
	}
}
