package micro.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import micro.bean.po.User;
import micro.commons.util.ShiroUtils;
import micro.web.util.Response;

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

	@RequestMapping(value = "/cros", method = RequestMethod.GET)
	public Map<String, Object> login(String userId) {
		User user = ShiroUtils.getUser();
		System.out.println("请求数据userId: " + userId + ", 用户对象: " + user);

		Map<String, Object> map = new HashMap<>(4);
		map.put("val", BigDecimal.ZERO);

		return Response.SUCCESS.newBuilder().toResult(map);
	}
}
