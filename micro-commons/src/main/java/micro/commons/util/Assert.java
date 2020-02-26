package micro.commons.util;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import micro.commons.exception.AssertException;

/**
 * 校验辅助类
 * 
 * @author gewx
 **/
public abstract class Assert {

	public static void neq(Number v1, Number v2, String msg) {
		if (v1.intValue() == v2.intValue()) {
			throw new AssertException(msg);
		}
	}

	public static void eq(String v1, String v2, String msg) {
		if (!v1.equals(v2)) {
			throw new AssertException(msg);
		}
	}

	public static void isNotBlank(String v1, String msg) {
		if (StringUtils.isBlank(v1)) {
			throw new AssertException(msg);
		}
	}

	public static void isNumeric(String v1, String msg) {
		if (!StringUtils.isNumeric(v1)) {
			throw new AssertException(msg);
		}
	}

	public static void isNotNull(Object v1, String msg) {
		if (v1 == null) {
			throw new AssertException(msg);
		}
	}

	public static void regEx(Pattern p, String v1, String msg) {
		if (!p.matcher(v1).matches()) {
			throw new AssertException(msg);
		}
	}
}
