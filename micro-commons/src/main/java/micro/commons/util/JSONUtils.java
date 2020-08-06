package micro.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import micro.commons.exception.JSONParseException;

/**
 * JSON工具类,基于Jackson
 * 
 * @author gewx
 **/

public enum JSONUtils {

	ALWAYS(false, Include.ALWAYS),

	NON_NULL(false, Include.NON_NULL);

	JSONUtils(boolean bool, JsonInclude.Include include) {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, bool);
		mapper.setSerializationInclusion(include);
	}

	private final ObjectMapper mapper;

	/**
	 * 解析对象为字符串
	 * 
	 * @author gewx
	 * @param val 对象
	 * @throws JSONParseException
	 * @return JSON字符串
	 **/
	public String toJSONString(Object val) {
		try {
			return mapper.writeValueAsString(val);
		} catch (JsonProcessingException e) {
			throw new JSONParseException(e);
		}
	}

	/**
	 * 将字符串解析为对象
	 * 
	 * @author gewx
	 * @param val   对象
	 * @param clazz 转换类型对象
	 * @throws JSONParseException
	 * @return Java对象
	 **/
	public <T> T toJavaObject(String val, Class<T> clazz) {
		try {
			return mapper.readValue(val, clazz);
		} catch (JsonProcessingException e) {
			throw new JSONParseException(e);
		}
	}

	/**
	 * 将字符串解析为对象
	 * 
	 * @author gewx
	 * @param val              对象
	 * @param clazz            转换类型对象
	 * @param parameterClasses 参数类型对象
	 * @throws JSONParseException
	 * @return Java对象
	 **/
	public <T> T toJavaObject(String val, Class<T> clazz, Class<?>... parameterClasses) {
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(clazz, parameterClasses);
			return mapper.readValue(val, javaType);
		} catch (JsonProcessingException e) {
			throw new JSONParseException(e);
		}
	}

	/**
	 * 将字符串解析为对象
	 * 
	 * @author gewx
	 * @param val  对象
	 * @param type 转换类型对象
	 * @throws JSONParseException
	 * @return Java对象
	 **/
	public <T> T toJavaObject(String val, TypeReference<T> type) {
		try {
			return mapper.readValue(val, type);
		} catch (JsonProcessingException e) {
			throw new JSONParseException(e);
		}
	}
}