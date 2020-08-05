package micro.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
}