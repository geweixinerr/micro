package micro.service.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import micro.plugin.eureka.EurekaInterface;

/**
 * Feign服务测试
 * 
 * @author gewx
 * **/
@Service
public class FeignServiceImpl {

	@Autowired
	private EurekaInterface eureka;
	
	public void callFeign() {
		Map<String,Object> result = eureka.hello("20200303", "35");
		System.out.println("服务调用结果： " + result);
	}
}
