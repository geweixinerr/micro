package micro.web.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Setter;
import micro.web.config.cros.CrosMetadata;

/**
 * Cros配置类, 此处Cros配置与JwtFilter一致[此处可不配置!!!]
 *
 * @author gewx
 */
@Configuration
@Setter
public class CrosAndJsonWebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	@Qualifier(value = "crosMetadata")
	private CrosMetadata crosMetadata;

	/**
	 * CROS 跨域配置
	 * 
	 * @author gewx
	 **/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(crosMetadata.getPathPattern())
				.allowedMethods(crosMetadata.getAllowMethods().toArray(new String[] {}))
				.allowedOrigins(crosMetadata.getOrigins())
				.allowedHeaders(crosMetadata.getAllowHeaders().toArray(new String[] {}))
				.exposedHeaders(crosMetadata.getExposedHeaders().toArray(new String[] {}))
				.allowCredentials(crosMetadata.isAllowCredentials());
	}

	/**
	 * JSON处理器
	 * 
	 * @author gewx
	 **/
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
	}

	/**
	 * JSON转换器
	 * 
	 * @author gewx
	 **/
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);

		simpleModule.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
			@Override
			public void serialize(BigDecimal decimal, JsonGenerator gen, SerializerProvider serializers)
					throws IOException {
				gen.writeString(decimal.toPlainString());
			}
		});

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.registerModule(simpleModule);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(mapper);
		return converter;
	}
}