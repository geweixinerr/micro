package micro.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统首页入口,心跳检测页
 * 
 * @author gewx
 **/
@RestController
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("/index");
		return view;
	}
}
