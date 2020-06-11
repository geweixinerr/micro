package micro.commons.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.ThreadSafe;

/**
 * Map工具类
 * 
 * @author gewx 
 * **/
@ThreadSafe 
public final class MapUtils {
	
	/**
	 * @author gewx
	 * @param Object 需要转换的对象
	 * @return string
	 * **/
	private static String getString(Object obj) {
		if (obj == null) {
			return StringUtils.EMPTY;
		} else {
			return obj.toString();
		}
	}
	
	/**
	 * @author gewx  验证map设定非空字段是否为空
	 * @param params
	 *            :目标map,args:校验数组
	 * @return boolean
	 * **/
	public static boolean isEmptyMap(Map<String, ?> params, String[] args) {
		if (params == null || args == null || params.isEmpty()
				|| args.length == 0) {
			return true;
		}

		boolean bool = false;
		for (String arg : args) {
			if (StringUtils.isBlank(getString(params.get(arg)))) {
				bool = true;
				break;
			}
		}
		return bool;
	}
	

	/**
	 * @author gewx 验证map设定非空字段是否为空
	 * @param params :目标map,args:校验数组
	 * @return boolean
	 **/
	public static boolean isNumericMap(Map<String, ?> params, String[] args) {
		if (params == null || args == null || params.isEmpty() || args.length == 0) {
			return false;
		}

		boolean bool = true;
		for (String arg : args) {
			if (!StringUtils.isNumeric(getString(params.get(arg)))) {
				bool = false;
				break;
			}
		}
		return bool;
	}
}
