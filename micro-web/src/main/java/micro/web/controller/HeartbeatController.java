package micro.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 心跳检测
 * 
 * @author gewx 2020.3.31
 **/
@Controller
public class HeartbeatController {

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 心跳检测
	 * 
	 * @author gewx
	 * @return 心跳视图
	 **/
	@GetMapping
	public ModelAndView index() {
		List<ServiceInstance> instances = discoveryClient.getInstances("psys-business-service");
		ServiceInstance instance = instances.get(0);
		System.out.println(instance.getServiceId());
		System.out.println(instance.getHost());
		System.out.println(instance.getUri());
		System.out.println(instance.getMetadata());
		System.out.println(instance.getScheme());
		ModelAndView view = new ModelAndView("/index");
		return view;
	}
}
