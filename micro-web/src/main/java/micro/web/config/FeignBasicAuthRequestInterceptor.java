package micro.web.config;

import java.util.Collection;
import java.util.Map;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import micro.commons.annotation.ThreadSafe;
import micro.commons.enums.ThreadContextEnum;

/**
 * Feign请求拦截器
 * 
 * @author gewx
 */
@ThreadSafe
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

	@SuppressWarnings("unchecked")
	@Override
	public void apply(RequestTemplate template) {
		Object val = ThreadContextEnum.HEADER.removeAndGetVal();
		if (val instanceof Map) {
			Map<String, Collection<String>> collections = (Map<String, Collection<String>>) val;
			template.headers(collections);
		}
	}
}
