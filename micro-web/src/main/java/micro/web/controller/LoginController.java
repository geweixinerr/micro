package micro.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import micro.commons.jwt.Jwt;
import micro.commons.log.MicroLogger;
import micro.service.demo.DemoService;
import micro.web.config.shiro.JwtToken;
import micro.web.util.Response;
import static micro.web.util.Response.GateWayCode;

/**
 * 系统首页入口
 * 
 * @author gewx
 **/
@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

	private static final MicroLogger LOGGER = new MicroLogger(LoginController.class);

	private static final String TOKEN = "token";

	/**
	 * token超时时间,单位/分钟
	 **/
	@Value("${token.expires}")
	private short expires;

	@Autowired
	private DemoService demoService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, Object> login(String userName, String password, HttpServletRequest req,
			HttpServletResponse resp) {
		final String methodName = "cros";
		LOGGER.enter(methodName, "cros客户端请求[start], userName: " + userName + ", password: " + password, false);
		demoService.syncDataById(1024);

		// 模拟登录成功
		try {
			JwtToken token = new JwtToken(Jwt.create().setUserName(userName).setExpires(expires).build().sign());
			SecurityUtils.getSubject().login(token);
			resp.setHeader(TOKEN, token.getToken());
		} catch (Exception ex) {
			// 登录失败
			return Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E9999).out("登录失败~").toResult();
		}

		LOGGER.exit(methodName, "cros客户端请求[end]");
		return Response.SUCCESS.newBuilder().toResult();
	}

}
