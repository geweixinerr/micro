package micro.dao.config;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
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
		// LettucePoolingClientConfiguration poolConfig = (LettucePoolingClientConfiguration)connectionFactory.getClientConfiguration();
		RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
