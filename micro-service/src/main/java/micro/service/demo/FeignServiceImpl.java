package micro.service.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import micro.plugin.eureka.EurekaPlugin;

/**
 * Feign服务测试
 * 
 * @author gewx
 **/
@Service
public class FeignServiceImpl {

	@Autowired
	private EurekaPlugin eureka;

	public void callFeign() {
		Map<String, Object> result = eureka.remoteCall("20200303", "35");
		System.out.println("Feign远程调用结果： " + result);
	}
}
