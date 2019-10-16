package micro.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class MySQLDruidConfig {
	
    private Long minEvictableIdleTimeMillis;
    
    private Long maxEvictableIdleTimeMillis;
}