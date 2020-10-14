package micro.commons.util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 基于okHttp3 工具类
 * 
 * @author gewx
 **/
public final class OkHttpClientUtils {

	/**
	 * 池化okHttp客户端组件
	 **/
	private static final OkHttpClient client = new OkHttpClient();

	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	/**
	 * 发送GET请求
	 * 
	 * @author gewx
	 * @param url 请求地址
	 * @throws IOException 抛出异常
	 * @return 响应值
	 **/
	public static String get(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

	/**
	 * 发送POST请求
	 * 
	 * @author gewx
	 * @param url  请求地址
	 * @param json 请求数据
	 * @throws IOException 抛出异常
	 * @return 响应值
	 **/
	public static String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
}
