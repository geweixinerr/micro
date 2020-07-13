package micro.commons.util;

import static micro.commons.util.StringUtil.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import micro.commons.annotation.ThreadSafe;
import micro.commons.enums.ThreadContextEnum;
import micro.commons.exception.BusinessRuntimeException;
import micro.commons.log.MicroLogger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Feign RPC调用工具类
 *
 * @author gewx
 **/
@ThreadSafe
public final class FeignRpcUtils {

	/**
	 * 日志组件
	 */
	private static final MicroLogger LOGGER = new MicroLogger(FeignRpcUtils.class);

	/**
	 * token
	 **/
	private static final String TOKEN = "token";

	/**
	 * SUCCESS
	 * **/
	private static final String SUCCESS = "0000";
	
	/**
	 * 响应消息体枚举
	 **/
	@Getter
	enum Response {

		SUCCESS("success", "服务调用成功/失败"),

		RESP_CODE("code", "响应码"),

		RESP_MSG("msg", "响应消息"),

		RESP_DATA("data", "响应数据");

		Response(String code, String comment) {
			this.code = code;
			this.comment = comment;
		}

		/**
		 * 编码
		 **/
		private String code;

		/**
		 * 注释
		 **/
		private String comment;
	}

	/**
	 * 解析响应数据
	 *
	 * @author gewx
	 * @param responseMap 响应结果集
	 * @return 解析后的数据
	 **/
	@SuppressWarnings("unchecked")
	public static Result getResult(Map<String, Object> responseMap) {
		Boolean success = Boolean.valueOf(getString(responseMap.get(Response.SUCCESS.getCode())));
		String code = getString(responseMap.get(Response.RESP_CODE.getCode()));
		String msg = getString(responseMap.get(Response.RESP_MSG.getCode()));
		Object data = responseMap.get(Response.RESP_DATA.getCode());
		StringBuilder token = new StringBuilder();
		Object headers = ThreadContextEnum.HEADER.removeAndGetVal();
		if (headers instanceof Map) {
			Map<String, Collection<String>> headerMap = (Map<String, Collection<String>>) headers;
			Collection<String> list = headerMap.get(TOKEN);
			token.append(CollectionUtils.isNotEmpty(list) ? list.stream().findFirst().get() : StringUtils.EMPTY);
		}
		Result result = new Result(success, code, msg, data, token.toString());
		return result;
	}

	/**
	 * 获取RPC结果集
	 * 
	 * @author liangd
	 * @param methodName    方法名
	 * @param map           RPC响应结果
	 * @param typeReference 转换
	 * @return 响应结果集
	 **/
	public static <T> T handleRpcResult(String methodName, Map<String, Object> map, TypeReference<T> typeReference) {
		LOGGER.enter(methodName, StringUtils.EMPTY);
		FeignRpcUtils.Result rpcResult = FeignRpcUtils.getResult(map);
		LOGGER.info(methodName, "RPC调用响应, rpcResult: " + rpcResult);
		if (rpcResult.isAllSuccess()) {
			Object data = rpcResult.getData();
			if (null != data) {
				String rpcResultDataJson = JSONObject.toJSONString(data);
				T t = JSONObject.parseObject(rpcResultDataJson, typeReference);
				LOGGER.info(methodName, "RPC调用响应, rpcData: " + t);
				LOGGER.exit(methodName, StringUtils.EMPTY);
				return t;
			}
			return null;
		} else {
			throw new BusinessRuntimeException("RPC调用失败或未查询到相关信息~");
		}
	}
	
	/**
	 * 设置token
	 * 
	 *  @author gewx
	 *  @param token 认证token
	 *  @return void
	 * **/
	public static void setToken(String token) {
		Collection<String> collections = new ArrayList<>();
		collections.add(token);
		Map<String, Collection<String>> headers = new HashMap<>();
		headers.put(TOKEN, collections);
		ThreadContextEnum.HEADER.setVal(headers); 
	}
	
	/**
	 * RPC响应结果集对象
	 **/
	@Getter
	@ToString
	public static class Result {

		@Getter(value = AccessLevel.NONE)
		private final Boolean success;

		private final String code;

		private final String msg;

		private final Object data;

		private final String token;

		public Result(Boolean success, String code, String msg, Object data, String token) {
			this.success = success;
			this.code = code;
			this.msg = msg;
			this.data = data;
			this.token = token;
		}

		public Boolean isSuccess() {
			return this.success;
		}

		/**
		 * 服务调用响应与业务响应同时成功
		 *
		 * @author gewx
		 **/
		public Boolean isAllSuccess() {
			return this.success && SUCCESS.equals(this.code);
		}
	}
}
