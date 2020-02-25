package micro.web.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import micro.web.util.Response;

/**
 * 解决Chrome Crob问题[Cross-Origin Read Blocking].
 * 
 * @author gewx
 **/
//@ControllerAdvice
public class ChromeCrobResponseBodyAdviceImpl implements ResponseBodyAdvice<Object> {

	@SuppressWarnings("static-access")
	private static final ThreadLocal<SimpleDateFormat> FORMAT = new ThreadLocal<>().withInitial(() -> {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	});

	private static final String CONTEXT_TYPE = "application";

	private static final String CONTEXT_SUB_TYPE = "json";

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (CONTEXT_TYPE.equals(selectedContentType.getType())
				&& CONTEXT_SUB_TYPE.equals(selectedContentType.getSubtype())) {
			// 设置自定义响应类型,绕过Crob检测
			response.getHeaders().add("Content-Type", "application/json-x");
		}

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
		mapper.setDateFormat(FORMAT.get());
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.registerModule(simpleModule);
		try {
			return mapper.writeValueAsString(body);
		} catch (JsonProcessingException ex) {
			return JSONObject.toJSONString(Response.FAIL.newBuilder().toResult());
		}
	}
}
