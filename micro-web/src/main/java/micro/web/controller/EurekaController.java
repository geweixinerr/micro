package micro.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import micro.service.demo.FeignServiceImpl;
import micro.web.util.Response;

/**
 * 系统首页入口,心跳检测页
 * 
 * @author gewx
 **/
@RequestMapping(value = "/eureka")
@RestController
public class EurekaController {	

	@Autowired
	private FeignServiceImpl feignClient;
	
	@RequestMapping(value = "/feignCall", method = RequestMethod.GET)
	public Map<String, Object> login() {
		feignClient.callFeign();
		return Response.SUCCESS.newBuilder().toResult();
	}
}
