package micro.dao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Druid数据源配置类
 * 
 * @author gewx
 **/
@Configuration
public class DruidDataSourceConfig {

	/**
	 * 可用处理器数量
	 **/
	private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

	@Autowired
	private MysqlDruidConfig druidConfig;

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DruidDataSource druidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		// druid bugs fix.
		dataSource.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
		dataSource.setMaxEvictableIdleTimeMillis(druidConfig.getMaxEvictableIdleTimeMillis());

		// performance optimizing
		dataSource.setInitialSize(CPU_NUM + 1);
		dataSource.setMinIdle(CPU_NUM + 1);
		dataSource.setMaxActive(CPU_NUM * 2);
		return dataSource;
	}

	@Bean
	public MysqlDruidConfig getDruidConfig() {
		return new MysqlDruidConfig();
	}
}
