package micro.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author gewx MyBatis 自动扫描装配
 * **/
@Configuration
@MapperScan(basePackages = { "micro.dao.intf" })
public class MyBatisConfig {

}
