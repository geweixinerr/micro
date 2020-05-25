package micro.commons.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import micro.commons.annotation.ThreadSafe;

/**
 * 分布式日志框架
 * 
 * @author gewx
 **/
@ThreadSafe
public final class MicroLogger {

	enum LOGGER {
		// 开始
		enter,

		// 退出
		exit,

		// 普通
		info,

		// 警告
		warn,

		// 异常
		error
	}

	private static final String MDC_STATE = "state";

	private final Logger logger;

	@SuppressWarnings("rawtypes")
	public MicroLogger(Class c) {
		this.logger = LoggerFactory.getLogger(c);
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param methodName 方法名
	 * @param msg        打印消息body
	 * @return void
	 **/
	public void enter(String methodName, String msg) {
		try {
			MDC.put(MDC_STATE, StringUtils.trimToEmpty(methodName) + " | " + LOGGER.enter);
			logger.info(msg);
		} finally {
			MDC.remove(MDC_STATE);
		}
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param methodName 方法名
	 * @return void
	 **/
	public void enter(String msg) {
		enter(null, msg);
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param methodName 方法名
	 * @param msg        打印消息body
	 * @return void
	 **/
	public void exit(String methodName, String msg) {
		try {
			MDC.put(MDC_STATE, StringUtils.trimToEmpty(methodName) + " | " + LOGGER.exit);
			logger.info(msg);
		} finally {
			MDC.remove(MDC_STATE);
		}
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param msg 打印消息body
	 * @return void
	 **/
	public void exit(String msg) {
		exit(null, msg);
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param methodName 方法名
	 * @param msg        打印消息body
	 * @return void
	 **/
	public void info(String methodName, String msg) {
		try {
			MDC.put(MDC_STATE, StringUtils.trimToEmpty(methodName));
			logger.info(msg);
		} finally {
			MDC.remove(MDC_STATE);
		}
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param msg 打印消息body
	 * @return void
	 **/
	public void info(String msg) {
		info(null, msg);
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param methodName 方法名
	 * @param msg        打印消息body
	 * @return void
	 **/
	public void warn(String methodName, String msg) {
		try {
			MDC.put(MDC_STATE, StringUtils.trimToEmpty(methodName));
			logger.warn(msg);
		} finally {
			MDC.remove(MDC_STATE);
		}
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param msg 打印消息body
	 * @return void
	 **/
	public void warn(String msg) {
		warn(null, msg);
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param methodName 方法名
	 * @param msg        打印消息body
	 * @return void
	 **/
	public void error(String methodName, String msg) {
		try {
			MDC.put(MDC_STATE, StringUtils.trimToEmpty(methodName));
			logger.error(msg);
		} finally {
			MDC.remove(MDC_STATE);
		}
	}

	/**
	 * 日志打印
	 * 
	 * @author gewx
	 * @param msg 打印消息body
	 * @return void
	 **/
	public void error(String msg) {
		error(null, msg);
	}
}
