package micro.web.exception;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import micro.commons.exception.AssertException;
import micro.commons.log.MicroLogger;
import micro.commons.util.StringUtil;
import micro.web.util.Response;
import micro.web.util.Response.GateWayCode;

/**
 * @author API网关异常处理器
 **/
@ControllerAdvice
@RestController
public class ApiGatewayGlobalExceptionHandler {

	/**
	 * 日志组件
	 **/
	private static final MicroLogger LOGGER = new MicroLogger(ApiGatewayGlobalExceptionHandler.class);

	/**
	 * 断言数据校验异常
	 * 
	 * @author gewx
	 **/
	@ExceptionHandler(value = { AssertException.class })
	public Map<String, Object> assertException(AssertException exception) {
		final String methodName = "assertException";
		LOGGER.error(methodName, "系统校验断言异常,message: " + StringUtil.getErrorText(exception));
		String message = ObjectUtils.defaultIfNull(exception.getMessage(), "系统错误~");
		return Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E9999).out(message).toResult();
	}

	/**
	 * Shrio权限控制异常
	 * 
	 * @author gewx
	 **/
	@ExceptionHandler(value = { UnauthorizedException.class })
	public Map<String, Object> unauthorizedException(UnauthorizedException exception) {
		final String methodName = "unauthorizedException";
		LOGGER.error(methodName, "Shiro权限异常,message: " + StringUtil.getErrorText(exception));
		return Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E0101).out("权限不足").toResult();
	}

	/**
	 * 运行时异常
	 * 
	 * @author gewx
	 **/
	@ExceptionHandler(value = { RuntimeException.class })
	public Map<String, Object> runtimeException(RuntimeException exception) {
		final String methodName = "runtimeException";
		LOGGER.error(methodName, "系统运行时异常,message: " + StringUtil.getErrorText(exception));
		String message = ObjectUtils.defaultIfNull(exception.getMessage(), "系统错误~");
		return Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E9999).out(message).toResult();
	}

	/**
	 * 兜底异常控制
	 * 
	 * @author gewx
	 **/
	@ExceptionHandler(value = { Exception.class })
	public Map<String, Object> exception(Exception exception) {
		final String methodName = "exception";
		LOGGER.error(methodName, "未知异常,message: " + StringUtil.getErrorText(exception));
		return Response.FAIL.newBuilder().addGateWayCode(GateWayCode.E9999).out("系统错误~").toResult();
	}
}
