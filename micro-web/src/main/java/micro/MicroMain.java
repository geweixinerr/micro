package micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author gewx 2019.10.14 微服务组件
 **/
@SpringBootApplication
public class MicroMain extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MicroMain.class);
	}
}
