package micro.web.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
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

/**
 * Cros配置类
 *
 * @author gewx
 */
@Configuration
@ConfigurationProperties(prefix = "cros.config")
public class CrosWebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * Cros路径匹配模式
	 **/
	private String pathPattern;

	/**
	 * Cros支持调用方法类型
	 **/
	private String methods;

	/**
	 * Cros支持的访问域
	 **/
	private String origins;

	/**
	 * Cors支持的访问请求头
	 **/
	private String allowedHeaders;

	/**
	 * Cors支持的访问响应头
	 **/
	private List<String> exposedHeaders;

	public void setPathPattern(String pathPattern) {
		this.pathPattern = pathPattern;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public void setOrigins(String origins) {
		this.origins = origins;
	}

	public void setAllowedHeaders(String allowedHeaders) {
		this.allowedHeaders = allowedHeaders;
	}

	public void setExposedHeaders(List<String> exposedHeaders) {
		this.exposedHeaders = exposedHeaders;
	}

	/**
	 * 跨域Filter 配置的详细信息说明如下：addMapping：配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
	 * allowedMethods：允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
	 * allowedOrigins：允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如：“http://www.aaa.com”，只有该域名可以访问我们的跨域资源。
	 * allowedHeaders：允许所有的请求header访问，可以自定义设置任意请求头信息。
	 * 
	 * @author gewx
	 **/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(pathPattern).allowedMethods(methods).allowedOrigins(origins).allowedHeaders(allowedHeaders)
				.exposedHeaders(exposedHeaders.toArray(new String[] {}));
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
	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
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