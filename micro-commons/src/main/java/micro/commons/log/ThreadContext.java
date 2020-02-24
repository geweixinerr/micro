package micro.commons.log;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 分布式日志框架,traceId数据容器
 * 
 * @author gewx
 **/
public final class ThreadContext {

	public static final TransmittableThreadLocal<String> TRACEID = new TransmittableThreadLocal<String>();

}
