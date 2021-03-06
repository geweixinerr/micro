package micro.web.config.shiro.filter;

import com.alibaba.fastjson.JSONObject;

import micro.commons.jwt.Jwt;
import micro.commons.jwt.JwtUtils;
import micro.commons.log.MicroLogger;
import micro.commons.util.StringUtil;
import micro.web.config.cros.CrosMetadata;
import micro.web.config.shiro.JwtToken;
import micro.web.util.Response;
import micro.web.util.Response.GateWayCode;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 * shiro + jwt权限控制
 * 
 * @author gewx
 **/
public final class JwtFilter extends BasicHttpAuthenticationFilter {

	/**
	 * 日志组件
	 **/
	private static final MicroLogger LOGGER = new MicroLogger(JwtFilter.class);
	
	/**
	 * 认证token
	 **/
	private static final String AUTH_TOKEN = "token";

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
		final String methodName = "onAccessDenied";
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String token = ObjectUtils.defaultIfNull(req.getHeader(AUTH_TOKEN), req.getParameter(AUTH_TOKEN));
		LOGGER.enter(methodName, "请求token: " + token);
		if (StringUtils.isBlank(token)) {
			String responseJson = JSONObject
					.toJSONString(Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0001).toResult());
			outFail(resp, responseJson);
			return false;
		}

		try {
			// 本地鉴权
			Jwt.JwtBean bean = JwtUtils.parseToken(token);
			boolean bool = JwtUtils.verifyToken(bean);
			if (!bool) {
				String responseJson = JSONObject
						.toJSONString(Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0002).toResult());
				outFail(resp, responseJson);
				return false;
			}

			// create new token
			JwtToken jwtToken = new JwtToken(
					Jwt.create().setUserName(bean.getUserName()).setExpires(bean.getExpires()).build().sign());
			getSubject(request, response).login(jwtToken);
			resp.setHeader(AUTH_TOKEN, jwtToken.getToken());
			LOGGER.exit(methodName, "响应token: " + jwtToken.getToken());
			return true;
		} catch (Exception ex) {
			String responseJson = JSONObject.toJSONString(Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0100)
					.out("鉴权失败,message: " + StringUtil.getErrorText(ex)).toResult());
			outFail(resp, responseJson);
			return false;
		}
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

		resp.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
		resp.setHeader("Access-Control-Allow-Methods",
				crosMetadata.getAllowMethods().stream().collect(Collectors.joining(", ")));
		resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
		resp.setHeader("Access-Control-Expose-Headers",
				crosMetadata.getExposedHeaders().stream().collect(Collectors.joining(", ")));
		resp.setHeader("Access-Control-Allow-Credentials", String.valueOf(crosMetadata.isAllowCredentials()));
		resp.setHeader("Access-Control-Max-Age", String.valueOf(crosMetadata.getMaxAge()));

		if (req.getMethod().equals(HttpMethod.OPTIONS.toString())) {
			resp.setStatus(HttpStatus.OK.value());
			return false;
		}

		return super.preHandle(req, resp);
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