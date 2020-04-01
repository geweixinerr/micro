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
@FeignClient(value = "psys-master-service", path = "/psys-master")
public interface EurekaPlugin {

	@GetMapping("/api/organization/getDeptByUserId")
	Map<String, Object> remoteCall(@RequestParam(name = "userId") String userId);
}
