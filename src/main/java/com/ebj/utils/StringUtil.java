package com.ebj.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringUtil {

	private static char[] numbersAndLetters = null;

	public static String notNullTrim(String strTemp) {
		if (strTemp == null) {
			return "";
		} else if ("null".equalsIgnoreCase(strTemp.trim())) {
			return "";
		} else {
			return strTemp.trim();
		}
	}

	public static List<String> strToList(String str, String seperator) {
		List<String> list = new ArrayList<String>();
		if (str == null || str.length() == 0 || seperator == null)
			return list;
		list.addAll(Arrays.asList(str.split(seperator)));
		return list;
	}

	public static List<String> strToList(String str) {
		return strToList(str, ",");
	}

	public static String listToStr(List<String> list, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0)
				sb.append(seperator);
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	public static String listToStr(List<String> list) {
		return listToStr(list, ",");
	}

	public static String appendSlash(String input) {
		if (!input.endsWith("/")) {
			return input + "/";
		}
		return input;
	}

	public static String randomString(String perfix, int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
				+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		// numbersAndLetters =
		// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return perfix + new String(randBuffer);
	}

	public static boolean isNotNullOrEmpty(String value) {
		return !isNullOrEmpty(value);
	}

	public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

	public static boolean isInteger(String intString) {
		try {
			Integer.parseInt(intString);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static double tryParseDouble(String doubleString, double defaultValue) {
		if (doubleString == null || doubleString.isEmpty()) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(doubleString);
		} catch (NumberFormatException e) {
			// do nothing.
		}
		return defaultValue;
	}

	public static double tryParseDouble(String doubleString,
                                        int scale,
                                        double defaultValue) {
		if (doubleString == null || doubleString.isEmpty()) {
			return defaultValue;
		}
		try {
			Double data = Double.parseDouble(doubleString);
            BigDecimal decimal = new BigDecimal(data).setScale(scale, BigDecimal.ROUND_HALF_UP);
            return decimal.doubleValue();
		} catch (NumberFormatException e) {
			// do nothing.
		}
		return defaultValue;
	}


	public static double tryParseDouble(String doubleString) {
		return tryParseDouble(doubleString, 0.0);
	}


	public static float tryParseFloat(String floatString, float defaultValue) {
		if (floatString == null || floatString.isEmpty()) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(floatString);
		} catch (NumberFormatException e) {
			// do nothing.
		}
		return defaultValue;
	}

    public static float tryParseFloat(String floatString,
                                        int scale,
                                        float defaultValue) {
        if (floatString == null || floatString.isEmpty()) {
            return defaultValue;
        }
        try {
            Float data = Float.parseFloat(floatString);
            BigDecimal decimal = new BigDecimal(data).setScale(scale, BigDecimal.ROUND_HALF_UP);
            return decimal.floatValue();
        } catch (NumberFormatException e) {
            // do nothing.
        }
        return defaultValue;
    }

	public static float tryParseFloat(String floatString) {
		return tryParseFloat(floatString, 0.0f);
	}

	public static int tryParseInt(String intString, int defaultValue) {
		if (intString == null || intString.isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(intString);
		} catch (NumberFormatException e) {
			// do nothing.
		}
		return defaultValue;
	}

	public static int tryParseInt(String intString) {
		return tryParseInt(intString, 0);
	}

    public static long tryParseLong(String longString, long defaultValue) {
        if (longString == null || longString.isEmpty()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException e) {
            // do nothing.
        }
        return defaultValue;
    }


	public static long tryParseLong(String longString) {
		return tryParseLong(longString, 0);
	}

	public static long ip2Long(String ip) {
		if (isNullOrEmpty(ip)) {
			return -1;
		}
		String[] addrArray = ip.split("\\.");
		long num = 0;
		for (int i = 0; i < addrArray.length; i++) {
			int power = 3 - i;
			num += ((Integer.parseInt(addrArray[i]) % 256) * Math.pow(256,
					power));
		}
		return num;
	}

	/**
	 * parse a substr of a string literal into long 
	 * @param str
	 * @param s: substr start index
	 * @param e: substr end index
	 * @return long number
	 */
	private static long str2long(String str, int s, int e) {
		int res = 0;
		while (s <= e) {
			res *= 10;
			res += (str.charAt(s) - '0');
			++s;
		}
		return res;
	}

	/**
	 * convert a ip str of format[A.B.C.D] into long number[A*256^3+B*256^2+C*256+D]
	 * high performance version
	 * @param ip: ip string
	 * @return ip long number rep
	 */
	public static long ip2long(String ip) {
		if (null == ip || ip.isEmpty()) {
			return -1;
		}
		long ipVal = 0;

		int s = 0;
		int e = ip.indexOf('.', s);
		if (-1 == e) {
			return -1;
		}
		// use long to avoid overflow

		ipVal += (str2long(ip, s, e - 1)) << 24; // A * (256 ^3)
		s = e + 1;
		e = ip.indexOf('.', s);
		if (-1 == e) {
			return -1;
		}

		ipVal += (str2long(ip, s, e - 1)) << 16; // B * (256 ^2)
		s = e + 1;
		e = ip.indexOf('.', s);
		if (-1 == e) {
			return -1;
		}

		ipVal += (str2long(ip, s, e - 1)) << 8; // C * (256)

		ipVal += (str2long(ip, e + 1, ip.length() - 1)); // D

		return ipVal;
	}
}
