package micro.web.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关响应设计
 * 
 * @author gewx
 **/
public enum Response {

	// 响应成功
	SUCCESS(true, "SUCCESS"),

	// 响应失败
	FAIL(false, "FAIL");

	Response(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	private final boolean success;

	private final String msg;

	public boolean isSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 响应消息构建方法
	 * 
	 * @author gewx
	 * @return 响应消息对象
	 **/
	public ResponseBuild newBuilder() {
		return new ResponseBuild(this);
	}

	/**
	 * 内部类,消息构建
	 * 
	 * @author gewx
	 **/
	public class ResponseBuild {
		private final boolean success;

		private String msg;

		ResponseBuild(Response response) {
			this.success = response.isSuccess();
			this.msg = response.getMsg();
		}

		public ResponseBuild out(String msg) {
			this.msg = msg;
			return this;
		}

		public Map<String, Object> toResult() {
			return buildMap();
		}

		/**
		 * 构建消息体
		 * 
		 * @author gewx
		 * @return 消息体
		 **/
		public <T extends java.io.Serializable> Map<String, Object> toResult(T model) {
			Map<String, Object> map = buildMap();
			map.put("data", model);
			return map;
		}

		/**
		 * 构建消息体
		 * 
		 * @author gewx
		 * @return 消息体
		 **/
		@SuppressWarnings("rawtypes")
		public <T extends java.util.Collection> Map<String, Object> toResult(T model) {
			Map<String, Object> map = buildMap();
			map.put("data", model);
			return map;
		}

		/**
		 * 构建消息体
		 * 
		 * @author gewx
		 * @return 消息体
		 **/
		@SuppressWarnings("rawtypes")
		public <T extends java.util.Map> Map<String, Object> toResult(T model) {
			Map<String, Object> map = buildMap();
			map.put("data", model);
			return map;
		}

		/**
		 * 构建消息体
		 * 
		 * @author gewx
		 * @return 消息体
		 **/
		private Map<String, Object> buildMap() {
			Map<String, Object> map = new HashMap<>(6);
			map.put("success", this.success);
			map.put("msg", this.msg);
			return map;
		}
	}
}
