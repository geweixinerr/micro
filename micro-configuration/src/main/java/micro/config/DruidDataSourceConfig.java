package micro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DruidDataSourceConfig {

	@Autowired
	private MySQLDruidConfig druidConfig;

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DruidDataSource druidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		//druid bugs fix.
		dataSource.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
		dataSource.setMaxEvictableIdleTimeMillis(druidConfig.getMaxEvictableIdleTimeMillis());
		return dataSource;
	}

	@Bean
	public MySQLDruidConfig getDruidConfig() {
		return new MySQLDruidConfig();
	}
}
