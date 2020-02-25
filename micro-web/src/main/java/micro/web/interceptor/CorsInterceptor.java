package micro.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Cros拦截器
 * 
 * @author gewx
 **/
public class CorsInterceptor implements HandlerInterceptor {

	private static final String HTTP_OPTIONS_METHOD = "OPTIONS";

	/**
	 * Cros预处理OPTIONS请求判断处理
	 **/
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
		// 存在跨域，会做options预请求，所以过滤掉options请求
		if (HTTP_OPTIONS_METHOD.equalsIgnoreCase(req.getMethod())) {
			return true;
		} else {
			// 默认放行,无处理
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler,
			ModelAndView modelAndView) {

	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {

	}
}
