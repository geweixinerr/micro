package micro.commons.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

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
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
			@Override
			public void serialize(BigDecimal decimal, JsonGenerator gen, SerializerProvider serializers)
					throws IOException {
				gen.writeString(decimal.toPlainString());
			}
		});

		simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date time, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(new DateTime(time).toString(format));
			}
		});

		simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				return format.parseDateTime(p.getValueAsString()).toDate();
			}
		});
		mapper.registerModule(simpleModule);
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