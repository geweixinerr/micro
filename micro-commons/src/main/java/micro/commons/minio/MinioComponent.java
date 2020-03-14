package micro.commons.minio;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.map.LazyMap;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

/**
 * Minio客户端封装组件
 * 
 * @author gewx
 **/
public enum MinioComponent {

	INSTANCE;

	/**
	 * 默认网络动作超时时间（读/写/连接）, 单位:秒
	 **/
	private static final long DEFAULT_TIMEOUT = 30;

	/**
	 * 延迟初始化Minio客户端Key
	 **/
	private static final String MINIO_CLIENT_KEY = "default";

	/**
	 * concurrentMap容器
	 **/
	private final Map<String, MinioClient> concurrentMap = new ConcurrentHashMap<>(4);

	/**
	 * lazy init MinioClient instance. MinioClient is ThreadSafe, support http1.1
	 * persistent connectionPool
	 **/
	@SuppressWarnings("unchecked")
	private final Map<String, MinioClient> container = LazyMap.decorate(concurrentMap, () -> {
		synchronized (this) {
			MinioClient client = concurrentMap.get(MINIO_CLIENT_KEY);
			if (client == null) {
				try {
					client = new MinioClient("", "", "");
					client.setTimeout(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
				} catch (InvalidEndpointException | InvalidPortException e) {
					throw new RuntimeException("Minio NetWork Connection wait...");
				}
				concurrentMap.put(MINIO_CLIENT_KEY, client);
			}
			// 此处返回client,会将concurrentMap.put(MINIO_CLIENT_KEY, client)此动作复写一遍,
			// 基于线程安全问题首先设置而后二度覆盖.
			return client;
		}
	});

	/**
	 * 传输对象
	 * 
	 * @author gewx
	 **/
	public void putObject() {
		container.get(MINIO_CLIENT_KEY);
	}
}
