package micro.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.ThreadSafe;

/**
 * 字符串操作辅助类
 * 
 * @author gewx
 **/
@ThreadSafe 
public final class StringUtil {

	/**
	 * 获取对象的字符串
	 * 
	 * @author gewx
	 * @param obj 需要转换的对象
	 * @return string
	 **/
	public static String getString(Object obj) {
		if (obj == null) {
			return StringUtils.EMPTY;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 异常栈字符串输出
	 * 
	 * @author gewx
	 * @param throwable 异常对象
	 * @return String
	 **/
	public static String getErrorText(Throwable throwable) {
		if (throwable == null) {
			return "ERROR,throwable is NULL!";
		}

		try (StringWriter strWriter = new StringWriter(512); PrintWriter writer = new PrintWriter(strWriter)) {
			throwable.printStackTrace(writer);
			StringBuffer sb = strWriter.getBuffer();
			return sb.toString();
		} catch (Exception ex) {
			return "ERROR!";
		}
	}
}
