package micro.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import micro.commons.log.MicroLogger;
import micro.service.demo.DemoService;
import micro.web.util.Response;

/**
 * 系统首页入口
 * @author gewx
 * **/
@RestController
public class IndexController {

	private static final MicroLogger LOGGER = new MicroLogger(IndexController.class);
	
	@Autowired
	private DemoService demoService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		final String methodName = "index.html";
		LOGGER.enter(methodName, "首页访问请求[start]", false);
		
		demoService.syncDataById(1024);
		
		ModelAndView view = new ModelAndView("/index");
		LOGGER.exit(methodName, "首页访问请求[end]");
		
		return view;
	}
	
	@RequestMapping(value = "/cros", method = RequestMethod.GET)
	public Map<String,Object> cors(String userId, HttpServletRequest req, HttpServletResponse resp) {
		final String methodName = "cros";
		LOGGER.enter(methodName, "cros客户端请求[start], params: " + userId, false);
		
		demoService.syncDataById(1024);
		
		LOGGER.exit(methodName, "cros客户端请求[end]");
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		//return map;
		resp.addHeader("token", "auth token");
		return Response.SUCCESS.newBuilder().toResult();
	}
	
}
