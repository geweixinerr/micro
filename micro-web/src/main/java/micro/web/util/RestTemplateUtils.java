package micro.web.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import micro.commons.annotation.ThreadSafe;

/**
 * RestTemplate 辅助工具类,简单密码登录
 * 
 * 使用案例: ResponseEntity<String> response = restTemplate.exchange(url,
 * HttpMethod.GET, entity, String.class); String str = response.getBody();
 * 
 * @author gewx
 **/
@ThreadSafe
public final class RestTemplateUtils {

	/**
	 * 包装简单密码登录
	 * 
	 * @author gewx
	 * @param userName 用户名
	 * @param password 密码
	 * @return HttpEntity 构建完成的请求包
	 **/
	public static HttpEntity<String> buildAuth(String userName, String password) {
		String safe = Stream.of(userName, password).collect(Collectors.joining(":"));
		String base64Safe = new String(Base64.encodeBase64(safe.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Safe);
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}
}
