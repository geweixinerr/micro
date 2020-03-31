package micro.plugin.eureka;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 外部渠道插件层
 * 
 * @author gewx
 **/
@FeignClient(value = "eureka-client-user-service")
public interface EurekaPlugin {

	@GetMapping("/index")
	Map<String, Object> remoteCall(@RequestParam(name = "userId") String userId,
			@RequestParam(name = "age") String age);

	@GetMapping("/org/dept/getDeptByUserId")
	Map<String, Object> remoteCall(@RequestParam(name = "userId") String userId);
}
