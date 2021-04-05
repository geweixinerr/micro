package micro.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.netflix.discovery.EurekaClient;

/**
 * 服务下线
 * 
 * @author gewx 2021.4.5
 **/
@Controller
public class ShutdownController {

	@Autowired
	private EurekaClient eurekaClient;

	@GetMapping(value = "/shutdown")
	public ResponseEntity<String> down(HttpServletResponse response) throws IOException {
		eurekaClient.shutdown();
		return ResponseEntity.ok("ok");
	}
}
