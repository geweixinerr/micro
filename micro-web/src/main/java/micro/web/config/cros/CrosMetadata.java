package micro.web.config.cros;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Cros配置元数据
 * 
 * @author gewx
 **/
@ConfigurationProperties(prefix = "cros.config")
@Setter
@Getter
@ToString
public class CrosMetadata {

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

}
