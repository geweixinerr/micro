package micro.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import micro.commons.log.MicroLogger;
import micro.service.demo.DemoService;

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
}
