package micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 服务组件引导类
 * 
 * @author gewx 2019.10.14 
 **/
@SpringBootApplication
@EnableFeignClients
public class MicroMain extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MicroMain.class);
	}
}
