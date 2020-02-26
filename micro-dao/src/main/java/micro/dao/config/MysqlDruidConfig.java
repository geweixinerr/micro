package micro.dao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Druid数据源配置参数元数据类
 * 
 * @author gewx
 * **/
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class MysqlDruidConfig {
	
    private Long minEvictableIdleTimeMillis;
    
    private Long maxEvictableIdleTimeMillis;
}