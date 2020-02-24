package micro.web.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关响应设计
 * 
 * @author gewx
 **/
public enum Response {

	SUCCESS(true, "SUCCESS"),

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

	public ResponseBuild newBuilder() {
		return new ResponseBuild(this);
	}

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

		public <T extends java.io.Serializable> Map<String, Object> toResult(T model) {
			Map<String, Object> map = buildMap();
			map.put("data", model);
			return map;
		}

		@SuppressWarnings("rawtypes")
		public <T extends java.util.Collection> Map<String, Object> toResult(T model) {
			Map<String, Object> map = buildMap();
			map.put("data", model);
			return map;
		}

		@SuppressWarnings("rawtypes")
		public <T extends java.util.Map> Map<String, Object> toResult(T model) {
			Map<String, Object> map = buildMap();
			map.put("data", model);
			return map;
		}

		private Map<String, Object> buildMap() {
			Map<String, Object> map = new HashMap<>(6);
			map.put("success", this.success);
			map.put("msg", this.msg);
			return map;
		}
	}
}
