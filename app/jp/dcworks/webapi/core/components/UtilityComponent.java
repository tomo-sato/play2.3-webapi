package jp.dcworks.webapi.core.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * ユーティリティコンポーネント。
 * @author tomo-sato
 */
public class UtilityComponent {

	/** Logger定義 */
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(UtilityComponent.class.getName());

	/** 日付フォーマット：yyyy/MM/dd HH:mm */
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS  = "yyyy/MM/dd HH:mm:ss";
	/** 日付フォーマット：yyyy/MM/dd HH:mm */
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM     = "yyyy/MM/dd HH:mm";
	/** 日付フォーマット：yyyy/MM/dd */
	public static final String DATE_FORMAT_YYYY_MM_DD           = "yyyy/MM/dd";
	/** 日付フォーマット：yyyy-MM-dd */
	public static final String DATE_FORMAT_YYYY_MM_DD_2         = "yyyy-MM-dd";
	/** 日付フォーマット：yyyy/MM */
	public static final String DATE_FORMAT_YYYY_MM              = "yyyy/MM";
	/** 日付フォーマット：yyyyMMddHHmmss */
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS       = "yyyyMMddHHmmss";
	/** 日付フォーマット：yyyyMMddHHmm */
	public static final String DATE_FORMAT_YYYYMMDDHHMM         = "yyyyMMddHHmm";
	/** 日付フォーマット：yyyyMMdd */
	public static final String DATE_FORMAT_YYYYMMDD             = "yyyyMMdd";
	/** 日付フォーマット：dd */
	public static final String DATE_FORMAT_DD                   = "dd";
	/** 日付フォーマット：HHmm */
	public static final String DATE_FORMAT_HHMM                 = "HHmm";

	/**
	 * 日付文字列を指定formatStrでDateに変換する。
	 * @param dateStr 日付文字列
	 * @param formatStr 日付フォーマット
	 * @return 変換後の日付を返す。
	 */
	public static Date toDate(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			LOGGER.warn(e.getMessage(), e);
			return null;
		}
		return date;
	}

	/**
	 * オブジェクトの構造をtoStringする。
	 * @param obj toString対象のオブジェクト
	 * @return toString結果
	 */
	public static String toStringField(Object obj) {
		StringBuffer buff = new StringBuffer(obj.getClass().getName()).append("{");

		Field[] fs = obj.getClass().getFields();
		StringBuffer fieldBuff = new StringBuffer();
		for (Field f : fs) {
			// フィールドの属性（修飾子）を取得
			int mod = f.getModifiers();

			// 静的要素は除外
			if (Modifier.isStatic(mod)) {
				continue;
			}

			try {
				fieldBuff.append(f.getName()).append("=");
				if (f.get(obj) == null) {
					fieldBuff.append("null, ");
				} else {
					if (f.get(obj) instanceof List) {
						StringBuffer listBuff = new StringBuffer("[");

						List<?> list = (List<?>) f.get(obj);
						for (Object tmpObj : list) {
							listBuff.append(tmpObj.toString()).append(", ");
						}
						if (listBuff.length() > 1) {
							listBuff.delete(listBuff.length()-2, listBuff.length());
						}

						fieldBuff.append(listBuff.toString()).append("], ");
					} else {
						fieldBuff.append(f.get(obj).toString()).append(", ");
					}
				}

			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOGGER.warn(e.getMessage(), e);
			} catch (Exception e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
		if (fieldBuff.length() > 1) {
			fieldBuff.delete(fieldBuff.length()-2, fieldBuff.length());
		}
		buff.append(fieldBuff);
		buff.append("}");
		return buff.toString();
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @return 変換後の値を返す。
	 */
	public static int toInt(String str) {
		return toInt(str, 0);
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @param defaultValue 変換できなかった場合の値
	 * @return 変換後の値を返す。（※返還できない場合、defaultValueの値を返す。）
	 */
	public static int toInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (isNumber(str)) {
			return NumberUtils.toInt(str, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * int整数値に変換する。（※変換失敗した時のみ「-1」を返す。）
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @return 変換後の値を返す。
	 */
	public static int toUnsignedInt(String str) {
		return toUnsignedInt(str, -1);
	}

	/**
	 * int整数値に変換する。（※変換失敗した時のみ「-1」または「defaultValue」を返す。）
	 * @param str 変換対象の文字列
	 * @param defaultValue 変換できなかった場合の値
	 * @return 変換後の値を返す。（※返還できない場合、defaultValueの値を返す。）
	 */
	public static int toUnsignedInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (NumberUtils.isDigits(str)) {
			return NumberUtils.toInt(str, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @return 変換後の値を返す。
	 */
	public static long toLong(String str) {
		return toLong(str, 0L);
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @param defaultValue 変換できなかった場合の値
	 * @return 変換後の値を返す。（※返還できない場合、defaultValueの値を返す。）
	 */
	public static long toLong(String str, long defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (isNumber(str)) {
			return NumberUtils.toLong(str, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * long整数値に変換する。（※変換失敗した時のみ「-1L」を返す。）
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @return 変換後の値を返す。
	 */
	public static long toUnsignedLong(String str) {
		return toUnsignedLong(str, -1L);
	}

	/**
	 * long整数値に変換する。（※変換失敗した時のみ「-1L」または「defaultValue」を返す。）
	 * @param str 変換対象の文字列
	 * @param defaultValue 変換できなかった場合の値
	 * @return 変換後の値を返す。（※返還できない場合、defaultValueの値を返す。）
	 */
	public static long toUnsignedLong(String str, long defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (NumberUtils.isDigits(str)) {
			return NumberUtils.toLong(str, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @return 変換後の値を返す。
	 */
	public static float toFloat(String str) {
		return toFloat(str, 0.0F);
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @param defaultValue 変換できなかった場合の値
	 * @return 変換後の値を返す。（※返還できない場合、defaultValueの値を返す。）
	 */
	public static float toFloat(String str, float defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (isNumber(str)) {
			return NumberUtils.toFloat(str, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @return 変換後の値を返す。
	 */
	public static double toDouble(String str) {
		return toDouble(str, 0.0D);
	}

	/**
	 * NumberUtils#isNumber()のチェックを挟んでNumberUtilsの変換関数を呼び出す。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str 変換対象の文字列
	 * @param defaultValue 変換できなかった場合の値
	 * @return 変換後の値を返す。（※返還できない場合、defaultValueの値を返す。）
	 */
	public static double toDouble(String str, double defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (isNumber(str)) {
			return NumberUtils.toDouble(str, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * NumberUtils.isNumber()の改良。
	 * <p>NumberUtils.isNumber()では16進数もtrueを返しているので、数字のみをチェックするよう調整。
	 * @see org.apache.commons.lang3.math.NumberUtils
	 * @param str チェック対象文字列
	 * @return 数値の場合trueを返す。
	 */
	public static boolean isNumber(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;

		int start = chars[0] == '-' ? 1 : 0;
		sz--;

		int i = start;
		while ((i < sz) || ((i < sz + 1) && (allowSigns) && (!foundDigit))) {
			if ((chars[i] >= '0') && (chars[i] <= '9')) {
				foundDigit = true;
				allowSigns = false;
			} else if (chars[i] == '.') {
				if ((hasDecPoint) || (hasExp)) {
					return false;
				}
				hasDecPoint = true;
			} else if ((chars[i] == '+') || (chars[i] == '-')) {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false;
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if ((chars[i] >= '0') && (chars[i] <= '9')) {
				return true;
			}
			if (chars[i] == '.') {
				if ((hasDecPoint) || (hasExp)) {
					return false;
				}
				return foundDigit;
			}
			if ((chars[i] == 'l') || (chars[i] == 'L')) {
				return (foundDigit) && (!hasExp) && (!hasDecPoint);
			}
			return false;
		}
		return (!allowSigns) && (foundDigit);
	}

	/**
	 * 引数のリストがnullまたはEmptyの場合true。
	 * <p>要素が存在する場合はfalse。
	 * @param list チェック対象
	 * @return 引数のリストがnullまたはEmptyの場合true。
	 */
	public static boolean isEmpty(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のMapがnullまたはEmptyの場合true。
	 * <p>要素が存在する場合はfalse。
	 * @param map チェック対象
	 * @return 引数のMapがnullまたはEmptyの場合true。
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のStringがnullまたはEmptyの場合true。
	 * <p>要素が存在する場合はfalse。
	 * @param str チェック対象
	 * @return 引数のStringがnullまたはEmptyの場合true。
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のDateがnullの場合true。
	 * <p>要素が存在する場合はfalse。
	 * @param date チェック対象
	 * @return 引数のDateがnullの場合true。
	 */
	public static boolean isEmpty(Date date) {
		if (date == null) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のIntegerがnullまたは0以下の場合true。
	 * <p>要素が存在する場合はfalse。
	 * @param i チェック対象
	 * @return 引数のIntegerがnullまたは0以下の場合true。
	 */
	public static boolean isEmpty(Integer i) {
		if (i == null || i.intValue() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のStringがnullまたはEmptyの場合true。
	 * <p>要素が存在する場合はfalse。
	 * <br>※trim結果に対してEmptyかどうかの判定も行う。
	 * @param str チェック対象
	 * @return 引数のStringがnullまたはEmptyの場合true。
	 */
	public static boolean isEmptyWithTrim(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
