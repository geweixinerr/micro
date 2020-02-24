package micro.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author gewx Http请求辅助类
 **/
public final class HttpRequestUtils {

	// 代理服务器请求头
	private static final String PROXY_HEADER = "X-Forwarded-For";

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
		if (StringUtils.isBlank(header)) {
			return request.getRemoteAddr();
		} else {
			String[] ipArray = StringUtils.split(header, ",");
			if (ArrayUtils.isNotEmpty(ipArray)) {
				return ipArray[0];
			} else {
				return request.getRemoteAddr();
			}
		}
	}
}
