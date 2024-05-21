package com.wacai.gateway.sdk.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>
 * 	clone from org.apache.commons.lang3.StringUtils, com.wacai.common.middleware.util.StringUtils
 * </p>
 * 
 *
 */
public class StringUtils {

	public static final String EMPTY_STRING = "";

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static final Pattern KVP_PATTERN = Pattern.compile("([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)"); //key value pair pattern.

	private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

	private StringUtils() {
	}

	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0)
			return true;
		return false;
	}

	/**
	 * is empty string.
	 *
	 * @param str source string.
	 * @return is empty.
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0)
			return true;
		return false;
	}

	/**
	 * is not empty string.
	 *
	 * @param str source string.
	 * @return is not empty.
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * @param s1
	 * @param s2
	 * @return equals
	 */
	public static boolean isEquals(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.equals(s2);
	}

	/**
	 * is integer string.
	 *
	 * @param str
	 * @return is integer
	 */
	public static boolean isInteger(String str) {
		if (str == null || str.length() == 0)
			return false;
		return INT_PATTERN.matcher(str).matches();
	}

	public static int parseInteger(String str) {
		return parseInteger(str,0);
	}
	
	public static int parseInteger(String str,int defaultValue) {
		if (!isInteger(str))
			return defaultValue;
		return Integer.parseInt(str);
	}

	/**
	 * Returns true if s is a legal Java identifier.<p>
	 * <a href="http://www.exampledepot.com/egs/java.lang/IsJavaId.html">more info.</a>
	 */
	public static boolean isJavaIdentifier(String s) {
		if (s.length() == 0 || !Character.isJavaIdentifierStart(s.charAt(0))) {
			return false;
		}
		for (int i = 1; i < s.length(); i++) {
			if (!Character.isJavaIdentifierPart(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * translat.
	 *
	 * @param src  source string.
	 * @param from src char table.
	 * @param to   target char table.
	 * @return String.
	 */
	public static String translat(String src, String from, String to) {
		if (isEmpty(src))
			return src;
		StringBuilder sb = null;
		int ix;
		char c;
		for (int i = 0, len = src.length(); i < len; i++) {
			c = src.charAt(i);
			ix = from.indexOf(c);
			if (ix == -1) {
				if (sb != null)
					sb.append(c);
			} else {
				if (sb == null) {
					sb = new StringBuilder(len);
					sb.append(src, 0, i);
				}
				if (ix < to.length())
					sb.append(to.charAt(ix));
			}
		}
		return sb == null ? src : sb.toString();
	}

	/**
	 * split.
	 *
	 * @param ch char.
	 * @return string array.
	 */
	public static String[] split(String str, char ch) {
		List<String> list = null;
		char c;
		int ix = 0, len = str.length();
		for (int i = 0; i < len; i++) {
			c = str.charAt(i);
			if (c == ch) {
				if (list == null)
					list = new ArrayList<String>();
				list.add(str.substring(ix, i));
				ix = i + 1;
			}
		}
		if (ix > 0)
			list.add(str.substring(ix));
		return list == null ? EMPTY_STRING_ARRAY : (String[]) list.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * join string.
	 *
	 * @param array String array.
	 * @return String.
	 */
	public static String join(String[] array) {
		if (array.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (String s : array)
			sb.append(s);
		return sb.toString();
	}

	/**
	 * join string like javascript.
	 *
	 * @param array String array.
	 * @param split split
	 * @return String.
	 */
	public static String join(String[] array, char split) {
		if (array.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				sb.append(split);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * join string like javascript.
	 *
	 * @param array String array.
	 * @param split split
	 * @return String.
	 */
	public static String join(String[] array, String split) {
		if (array.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				sb.append(split);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	public static String join(Collection<String> coll, String split) {
		if (coll.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (String s : coll) {
			if (isFirst)
				isFirst = false;
			else
				sb.append(split);
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * parse key-value pair.
	 *
	 * @param str           string.
	 * @param itemSeparator item separator.
	 * @return key-value map;
	 */
	private static Map<String, String> parseKeyValuePair(String str, String itemSeparator,boolean decoding) {
		String[] tmp = str.split(itemSeparator);
		Map<String, String> map = new HashMap<String, String>(tmp.length);
		for (int i = 0; i < tmp.length; i++) {
			Matcher matcher = KVP_PATTERN.matcher(tmp[i]);
			if (matcher.matches() == false)
				continue;
			if(decoding) {
				try {
					map.put(matcher.group(1), URLDecoder.decode(matcher.group(2), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}else {
				map.put(matcher.group(1), matcher.group(2));
			}
		}
		return map;
	}

	

	/**
	 * parse query string to Parameters.
	 *
	 * @param qs query string.
	 * @return Parameters instance.
	 */
	public static Map<String, String> parseQueryString(String qs, boolean decoding) {
		if (qs == null || qs.length() == 0)
			return new HashMap<String, String>();
		return parseKeyValuePair(qs, "\\&", decoding);
	}

	
	/**
	 * 
	 * @param ps
	 * @param encoding
	 * @return
	 */
	public static String toQueryString(Map<String, String> ps,boolean encoding) {
		StringBuilder buf = new StringBuilder();
		if (ps != null && ps.size() > 0) {
			for (Map.Entry<String, String> entry : new TreeMap<String, String>(ps).entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key != null && key.length() > 0
						&& value != null && value.length() > 0) {
					if (buf.length() > 0) {
						buf.append("&");
					}
					buf.append(key);
					buf.append("=");
					if(encoding) {
						try {
							buf.append(URLEncoder.encode(value,"utf-8"));
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
					}else {
						buf.append(value);
					}
				}
			}
		}
		return buf.toString();
	}

	public static byte[] getByteQuiet(String str, String charSet) {
		try {
			return str.getBytes(charSet);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static final int INDEX_NOT_FOUND = -1;

	 /**
    * <p>Replaces a String with another String inside a larger String,
    * for the first {@code max} values of the search String.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *, *)         = null
    * StringUtils.replace("", *, *, *)           = ""
    * StringUtils.replace("any", null, *, *)     = "any"
    * StringUtils.replace("any", *, null, *)     = "any"
    * StringUtils.replace("any", "", *, *)       = "any"
    * StringUtils.replace("any", *, *, 0)        = "any"
    * StringUtils.replace("abaa", "a", null, -1) = "abaa"
    * StringUtils.replace("abaa", "a", "", -1)   = "b"
    * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
    * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
    * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
    * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
    * </pre>
    *
    * @param text  text to search and replace in, may be null
    * @param searchString  the String to search for, may be null
    * @param replacement  the String to replace it with, may be null
    * @param max  maximum number of values to replace, or {@code -1} if no maximum
    * @return the text with any replacements processed,
    *  {@code null} if null String input
    */
   public static String replace(final String text, final String searchString, final String replacement, int max) {
       if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
           return text;
       }
       int start = 0;
       int end = text.indexOf(searchString, start);
       if (end == INDEX_NOT_FOUND) {
           return text;
       }
       final int replLength = searchString.length();
       int increase = replacement.length() - replLength;
       increase = increase < 0 ? 0 : increase;
       increase *= max < 0 ? 16 : max > 64 ? 64 : max;
       final StringBuilder buf = new StringBuilder(text.length() + increase);
       while (end != INDEX_NOT_FOUND) {
           buf.append(text.substring(start, end)).append(replacement);
           start = end + replLength;
           if (--max == 0) {
               break;
           }
           end = text.indexOf(searchString, start);
       }
       buf.append(text.substring(start));
       return buf.toString();
   }


	// Replacing
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, once.
	 * </p>
	 *
	 * <p>
	 * A {@code null} reference passed to this method is a no-op.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.replaceOnce(null, *, *)        = null
	 * StringUtils.replaceOnce("", *, *)          = ""
	 * StringUtils.replaceOnce("any", null, *)    = "any"
	 * StringUtils.replaceOnce("any", *, null)    = "any"
	 * StringUtils.replaceOnce("any", "", *)      = "any"
	 * StringUtils.replaceOnce("aba", "a", null)  = "aba"
	 * StringUtils.replaceOnce("aba", "a", "")    = "ba"
	 * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
	 * </pre>
	 *
	 * @see #replace(String text, String searchString, String replacement, int max)
	 * @param text         text to search and replace in, may be null
	 * @param searchString the String to search for, may be null
	 * @param replacement  the String to replace with, may be null
	 * @return the text with any replacements processed, {@code null} if null String
	 *         input
	 */
	public static String replaceOnce(final String text, final String searchString, final String replacement) {
		return replace(text, searchString, replacement, 1);
	}
	
   /**
    * <p>Replaces all occurrences of a String within another String.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *)        = null
    * StringUtils.replace("", *, *)          = ""
    * StringUtils.replace("any", null, *)    = "any"
    * StringUtils.replace("any", *, null)    = "any"
    * StringUtils.replace("any", "", *)      = "any"
    * StringUtils.replace("aba", "a", null)  = "aba"
    * StringUtils.replace("aba", "a", "")    = "b"
    * StringUtils.replace("aba", "a", "z")   = "zbz"
    * </pre>
    *
    * @see #replace(String text, String searchString, String replacement, int max)
    * @param text  text to search and replace in, may be null
    * @param searchString  the String to search for, may be null
    * @param replacement  the String to replace it with, may be null
    * @return the text with any replacements processed,
    *  {@code null} if null String input
    */
   public static String replace(final String text, final String searchString, final String replacement) {
       return replace(text, searchString, replacement, -1);
   }

}
