package micro.web.config.cros;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cros配置Bean
 * 
 * @author gewx
 **/
@Configuration
public class CrosConfig {

	/**
	 * cros 配置元数据
	 * 
	 * @author gewx
	 **/
	@Bean(value = "crosMetadata")
	public CrosMetadata crosMetadata() {
		return new CrosMetadata();
	}
}
