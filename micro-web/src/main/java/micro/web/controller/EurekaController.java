package micro.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	private RestTemplate rest;
	
	@Autowired
	private FeignServiceImpl feignClient;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getIndex", method = RequestMethod.GET)
	public Map<String, Object> login() {
		Map<String,String> params = new HashMap<>();
		params.put("userId", "geweixin");
		params.put("age", "35");
		
		long start = System.currentTimeMillis();
		Map<String,Object> result = rest.getForObject("http://EUREKA-CLIENT-USER-SERVICE/index?userId={userId}&age={age}",Map.class,params);
		long end = System.currentTimeMillis();
		System.out.println("响应: " + result + ", 耗时: " + (end - start));
		
		feignClient.callFeign();
		return Response.SUCCESS.newBuilder().toResult();
	}
}
