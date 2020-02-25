package micro.web.config.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

/**
 * shiro权限控制
 * 
 * @author gewx
 **/
public class JwtFilter extends BasicHttpAuthenticationFilter {

	/**
	 * Cros预检OPTIONS请求,常量标记
	 **/
	private static final String HTTP_OPTIONS = "OPTIONS";

	/**
	 * ajax请求标记头
	 **/
	private static final String AJAX_REQUEST_HEADER = "X-Requested-With";

	/**
	 * ajax请求标记头数值
	 **/
	private static final String AJAX_REQUEST_HEADER_VAL = "XMLHttpRequest";

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		return false;
	}

	/**
	 *
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

		return true;
	}

	/**
	 * 对跨域提供支持
	 * 
	 * @author gewx
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		// 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
		if (req.getMethod().equals(HTTP_OPTIONS)) {
			return false;
		}
		return super.preHandle(request, response);
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String ajaxHeader = req.getHeader(AJAX_REQUEST_HEADER);

		if (AJAX_REQUEST_HEADER_VAL.equals(StringUtils.trimToEmpty(ajaxHeader))) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			resp.setDateHeader("expries", -1);
			resp.setHeader("Cache-Control", "no-cache");
			resp.setHeader("Pragma", "no-cache");

			/*
			 * JSONObject responseJson = new JSONObject(); Map<String, Object> val =
			 * Response.FAIL.newBuilder().print("权限不足~").toResult();
			 * responseJson.putAll(val); PrintWriter writer = response.getWriter();
			 * writer.write(responseJson.toJSONString()); writer.flush(); writer.close();
			 */
			// TODO 抛出异常
		} else {
			super.redirectToLogin(request, response);
		}
	}
}
