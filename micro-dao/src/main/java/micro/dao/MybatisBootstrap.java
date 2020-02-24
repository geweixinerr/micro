package micro.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 引导自动扫描装配
 * @author gewx 
 * **/
@Configuration
@MapperScan(basePackages = { "micro.dao.intf" })
public class MybatisBootstrap {

}
