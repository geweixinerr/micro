package micro.web.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.ThreadSafe;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 获取header请求头列表
	 * 
	 * @author gewx
	 * @param request 请求对象
	 * @return 请求头
	 **/
	public static Map<String, Object> getHeaders(HttpServletRequest request) {
		List<String> headersList = new ArrayList<>(32);
		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			headersList.add(en.nextElement());
		}
		Map<String, Object> headersMap = new HashMap<>(32);
		headersList.forEach(val -> {
			headersMap.put(val, request.getHeader(val));
		});
		return headersMap;
	}
}
