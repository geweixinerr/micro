package micro.web.exception;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import micro.commons.exception.AssertException;
import micro.commons.log.MicroLogger;
import micro.web.util.Response;

/**
 * @author API网关异常处理器
 **/
@ControllerAdvice
@RestController
public class ApiGatewayGlobalExceptionHandler {

	/**
	 * 日志组件
	 * **/
	private static final MicroLogger LOGGER = new MicroLogger(ApiGatewayGlobalExceptionHandler.class);

	/**
	 * @author gewx 断言数据校验异常
	 **/
	@ExceptionHandler(value = { AssertException.class })
	public Map<String, Object> assertException(AssertException exception) {
		System.out.println("捕获异常");
		return Response.FAIL.newBuilder().out("测试").toResult();
	}
	
	/**
	 * @author gewx 断言数据校验异常
	 **/
	@ExceptionHandler(value = { RuntimeException.class })
	public Map<String, Object> runtimeException(RuntimeException exception) {
		System.out.println("捕获异常");
		return Response.FAIL.newBuilder().out("测试").toResult();
	}
}
