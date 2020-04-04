package micro.dao.config;

import java.io.Serializable;
import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 
 * @author gewx
 **/
@Configuration
public class RedisConfig {

	/**
	 * 配置Lettuce Redis客户端
	 * 
	 * @author gewx
	 * @return redisTemplate
	 **/
	@Bean
	public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(connectionFactory);
	
		LettucePoolingClientConfiguration poolConfig = (LettucePoolingClientConfiguration) connectionFactory.getClientConfiguration();
		System.out.println("连接池: " + poolConfig.getPoolConfig().getMaxIdle());
		return template;
	}
}
