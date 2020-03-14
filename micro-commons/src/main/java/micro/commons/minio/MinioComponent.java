package micro.commons.minio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

/**
 * minio客户端组件
 * 
 * @author gewx
 **/
@Component
public class MinioComponent {

	/**
	 * minio服务器地址
	 **/
	@Value("${minio.url}")
	private String minioUrl;

	/**
	 * minio服务器端口号
	 **/
	@Value("${minio.port}")
	private int port;

	/**
	 * access Key
	 **/
	@Value("${minio.accessKey}")
	private String accessKey;

	/**
	 * secret Key
	 **/
	@Value("${minio.secretKey}")
	private String secretKey;

	/**
	 * connectTimeout
	 **/
	@Value("${minio.connectTimeout}")
	private long connectTimeout;

	/**
	 * writeTimeout
	 **/
	@Value("${minio.writeTimeout}")
	private long writeTimeout;

	/**
	 * readTimeout
	 **/
	@Value("${minio.readTimeout}")
	private long readTimeout;

	/**
	 * default network I/O timeout unit:milliseconds
	 **/
	private static final long UNIT = 1000;

	/**
	 * minioClient Key
	 **/
	private static final String MINIO_CLIENT_KEY = "defaultClient";

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
					client = new MinioClient(minioUrl, port, accessKey, secretKey);
					client.setTimeout(connectTimeout * UNIT, writeTimeout * UNIT, readTimeout * UNIT);
				} catch (InvalidEndpointException | InvalidPortException e) {
					throw new RuntimeException("Minio NetWork Connection wait...");
				}
				concurrentMap.put(MINIO_CLIENT_KEY, client);
			}
			return client;
		}
	});

	/**
	 * 上传对象,对象上传完毕自动关闭输入流
	 * 
	 * @author gewx
	 * @param bucketName 桶名称
	 * @param objectName 对象名称
	 * @param input      输入流
	 * @throws Exception
	 * @return void
	 **/
	public void putObject(String bucketName, String objectName, InputStream input) throws Exception {
		MinioClient minioClient = container.get(MINIO_CLIENT_KEY);
		try (final InputStream _input = input) {
			minioClient.putObject(bucketName, objectName, _input, null, null, null, null);
		}
	}

	/**
	 * 上传对象,对象上传完毕自动关闭输入流
	 * 
	 * @author gewx
	 * @param bucketName 桶名称
	 * @param objectName 对象名称
	 * @param byteArray  字节数组
	 * @throws Exception
	 * @return void
	 **/
	public void putObject(String bucketName, String objectName, byte[] byteArray) throws Exception {
		MinioClient minioClient = container.get(MINIO_CLIENT_KEY);
		try (final ByteArrayInputStream input = new ByteArrayInputStream(byteArray)) {
			minioClient.putObject(bucketName, objectName, input, null, null, null, null);
		}
	}

	/**
	 * 上传对象,对象上传完毕自动关闭输入流
	 * 
	 * @author gewx
	 * @param bucketName 桶名称
	 * @param objectName 对象名称
	 * @param filePath   本地文件路径
	 * @throws Exception
	 * @return void
	 **/
	public void putObject(String bucketName, String objectName, String filePath) throws Exception {
		MinioClient minioClient = container.get(MINIO_CLIENT_KEY);
		minioClient.putObject(bucketName, objectName, filePath, null, null, null, null);
	}

	/**
	 * 获取对象,对象下载完毕后自动关闭输入流
	 * 
	 * @author gewx
	 * @param bucketName 桶名称
	 * @param objectName 对象名称
	 * @throws Exception
	 * @return byte[]
	 **/
	public byte[] getObject(String bucketName, String objectName) throws Exception {
		MinioClient minioClient = container.get(MINIO_CLIENT_KEY);
		try (final InputStream input = minioClient.getObject(bucketName, objectName)) {
			byte[] byteArray = IOUtils.toByteArray(input);
			return byteArray;
		}
	}

	/**
	 * 检测桶是否存在
	 * 
	 * @author gewx
	 * @param bucketName 桶名称
	 * @throws Exception
	 * @return boolean
	 **/
	public boolean checkBucket(String bucketName) throws Exception {
		MinioClient minioClient = container.get(MINIO_CLIENT_KEY);
		return minioClient.bucketExists(bucketName);
	}

	/**
	 * 创建桶
	 * 
	 * @author gewx
	 * @param bucketName 桶名称
	 * @throws Exception
	 * @return void
	 **/
	public void createBucket(String bucketName) throws Exception {
		MinioClient minioClient = container.get(MINIO_CLIENT_KEY);
		minioClient.makeBucket(bucketName);
	}
}
