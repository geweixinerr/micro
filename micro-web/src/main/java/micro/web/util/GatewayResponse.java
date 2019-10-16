package micro.web.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gewx 2019.9.20 网关API响应
 **/
public enum GatewayResponse {

	SUCCESS(GatewayApiCode.C_10000.getCode(), GatewayApiCode.C_10000.getMsg()),

	FAIL;

	GatewayResponse() {

	}

	GatewayResponse(String _code, String _msg) {
		this.code = _code;
		this.msg = _msg;
	}



	/**
	 * 网关响应码枚举
	 * **/
	public enum GatewayApiCode {
		
		C_10000("10000", "接口调用成功"),

		C_20000("20000", "服务不可用"), // 可等待重试

		C_20001("20001", "授权权限不足"), // 譬如：令牌失效等

		C_40001("40001", "缺失必填参数"),

		C_40002("40002", "非法参数"),

		C_40004("40004", "业务处理失败"),

		C_40006("40006", "权限不足"), // 譬如:当前API用户不具备访问权限

		C_99999("99999", "系统错误"); // 通用响应码

		private String code;

		private String msg;

		GatewayApiCode(String _code, String _msg) {
			this.code = _code;
			this.msg = _msg;
		}

		public String getMsg() {
			return this.msg;
		}

		public String getCode() {
			return this.code;
		}
	}
	
	private String code;

	private String sub_code;

	private String msg;

	private String sub_msg;



	public GatewayResponse buildGatewayCode(GatewayApiCode _code) {
		this.code = _code.getCode();
		this.msg = _code.getMsg();
		return this;
	}

	public GatewayResponse setMsg(String _msg) {
		this.msg = _msg;
		return this;
	}

	public GatewayResponse addSubCode(String _code) {
		this.sub_code = _code;
		return this;
	}

	public GatewayResponse addSubMsg(String _msg) {
		this.sub_msg = _msg;
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
		Map<String, Object> map = new HashMap<>();
		map.put("code", this.code);
		map.put("msg", this.msg);
		if (StringUtils.isNotBlank(this.sub_code)) {
			map.put("sub_code", this.sub_code);
			map.put("sub_msg", this.sub_msg);
		}
		return map;
	}
}
