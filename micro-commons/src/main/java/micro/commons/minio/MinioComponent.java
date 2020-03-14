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
	 * default network I/O timeout is 30 seconds
	 **/
	private static final long DEFAULT_TIMEOUT = 30;

	/**
	 * minioClient Key
	 **/
	private static final String MINIO_CLIENT_KEY = "default";

	/**
	 * concurrentMap容器
	 **/
	private final Map<String, MinioClient> concurrentMap = new ConcurrentHashMap<>(4);

	/**
	 * lazy init MinioClient instance. minioClient is threadSafe, support http1.1
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
				// mark star
				concurrentMap.put(MINIO_CLIENT_KEY, client);
			}
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
