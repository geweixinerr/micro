package micro.commons.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Map工具类
 * 
 * @author gewx 
 * **/
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
}
