package micro.web.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.ThreadSafe;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gewx Http请求辅助类
 **/
@ThreadSafe
public final class HttpRequestUtils {

	/**
	 * 代理服务器请求头
	 **/
	private static final String PROXY_HEADER = "X-Forwarded-For";

	private static final String X_REAL_IP = "X-Real-IP";

	private HttpRequestUtils() {

	}

	/**
	 * 获得客户真实IP(支持代理)
	 * 
	 * @author gewx
	 * @param request 请求对象
	 * @return Ip地址
	 **/
	public static String getRemoteAddrIp(HttpServletRequest request) {
		String header = request.getHeader(PROXY_HEADER);
		String realIp = request.getHeader(X_REAL_IP);
		if (StringUtils.isAllBlank(header, realIp)) {
			return request.getRemoteAddr();
		} else {
			if (StringUtils.isNotBlank(realIp)) {
				return realIp;
			}

			String[] ipArray = StringUtils.split(header, ",");
			if (ArrayUtils.isNotEmpty(ipArray)) {
				return ipArray[0];
			} else {
				return request.getRemoteAddr();
			}
		}
	}
}
