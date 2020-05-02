package micro.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import micro.bean.po.User;
import micro.commons.jwt.Jwt;
import micro.commons.log.MicroLogger;
import micro.commons.page.Pages;
import micro.service.demo.DemoService;
import micro.web.util.Response;

/**
 * 系统首页入口
 * 
 * @author gewx
 **/
@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

	private static final MicroLogger LOGGER = new MicroLogger(LoginController.class);

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
		
		Pages<User> page = demoService.listUser();
		LOGGER.info("数据集: " + page);
        String token = Jwt.create().setUserName("geweixin").setExpires((short)30).build().sign();
        resp.setHeader("token", token);
        
		LOGGER.exit(methodName, "cros客户端请求[end]");
		return Response.SUCCESS.newBuilder().toResult();
	}

}
