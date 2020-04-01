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
		Map<String, Object> _result = eureka.remoteCall("GEWEIXIN2020");
		System.out.println("result---> " + _result);
	}
}
