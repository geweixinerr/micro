package micro.web.config.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import com.alibaba.fastjson.JSONObject;

import micro.commons.jwt.Jwt;
import micro.commons.util.JwtUtils;
import micro.web.config.cros.CrosMetadata;
import micro.web.config.shiro.JwtToken;
import micro.web.util.Response;
import micro.web.util.Response.GateWayCode;

/**
 * shiro + jwt权限控制
 * 
 * @author gewx
 **/
public final class JwtFilter extends BasicHttpAuthenticationFilter {

	/**
	 * 认证token
	 **/
	private static final String AUTH_TOKEN = "token";

	/**
	 * Cros预检OPTIONS请求,常量标记
	 **/
	private static final String HTTP_OPTIONS = "OPTIONS";

	/**
	 * ajax请求标记头,跨域调用需要手工设置请求头
	 **/
	private static final String AJAX_REQUEST_HEADER = "X-Requested-With";

	/**
	 * ajax请求标记头数值
	 **/
	private static final String AJAX_REQUEST_HEADER_VAL = "XMLHttpRequest";

	private final CrosMetadata crosMetadata;

	public JwtFilter(CrosMetadata crosMetadata) {
		this.crosMetadata = crosMetadata;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}

	/**
	 * jwt 拦截具体动作
	 * 
	 * @author gewx
	 **/
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 从请求头或者URL当中获取token
		String token = ObjectUtils.defaultIfNull(req.getHeader(AUTH_TOKEN), req.getParameter(AUTH_TOKEN));
		if (StringUtils.isBlank(token)) {
			String responseJson = JSONObject
					.toJSONString(Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0001).toResult());
			outFail(resp, responseJson);
			return false;
		}

		try {
			try {
				boolean bool = JwtUtils.verifyToken(token);
				if (!bool) {
					String responseJson = JSONObject.toJSONString(
							Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0002).out("鉴权失败~").toResult());
					outFail(resp, responseJson);
					return false;
				}
			} finally {
				/**
				 * create new token 无论认证通过与否,token必须具备一次消费属性
				 **/
				Jwt.JwtBean bean = JwtUtils.parseToken(token);
				JwtToken jwtToken = new JwtToken(
						Jwt.create().setUserName(bean.getUserName()).setExpires(30).build().sign());
				getSubject(request, response).login(jwtToken);
				resp.setHeader(AUTH_TOKEN, jwtToken.getToken());
			}
		} catch (Exception ex) {
			String responseJson = JSONObject.toJSONString(
					Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E9999).out("token 认证失败~").toResult());
			outFail(resp, responseJson);
			return false;
		}
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
		HttpServletResponse resp = (HttpServletResponse) response;

		resp.setHeader("Access-control-Allow-Origin", crosMetadata.getOrigins());
		resp.setHeader("Access-Control-Allow-Methods", crosMetadata.getMethods());
		resp.setHeader("Access-Control-Allow-Headers", crosMetadata.getAllowedHeaders());
		resp.setHeader("Access-Control-Expose-Headers",
				crosMetadata.getExposedHeaders().stream().collect(Collectors.joining(",")));

		// Cros跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
		if (HTTP_OPTIONS.equals(req.getMethod())) {
			return false;
		}
		return super.preHandle(request, response);
	}

	/**
	 * 请求转发
	 * 
	 * @author gewx
	 **/
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String ajaxHeader = req.getHeader(AJAX_REQUEST_HEADER);
		if (AJAX_REQUEST_HEADER_VAL.equals(StringUtils.trimToEmpty(ajaxHeader))) {
			String responseJson = JSONObject
					.toJSONString(Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0001).toResult());
			outFail(resp, responseJson);
		} else {
			super.redirectToLogin(request, response);
		}
	}

	/**
	 * 输出响应流
	 * 
	 * @author gewx
	 * @param resp 响应对象, message 响应消息
	 * @return void
	 * @throws IOException
	 **/
	private void outFail(HttpServletResponse resp, String message) throws IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.setDateHeader("expries", -1);
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		PrintWriter writer = resp.getWriter();
		writer.write(message);
		writer.flush();
		writer.close();
	}
}
