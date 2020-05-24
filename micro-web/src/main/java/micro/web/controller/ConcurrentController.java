package micro.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import micro.commons.concurrent.ConcurrentLock;
import micro.commons.log.MicroLogger;
import micro.web.util.Response;

/**
 * 分布式复合锁案例
 * 
 * @author gewx
 **/
@RestController
@RequestMapping("/concurrent")
public class ConcurrentController {

	/**
	 * 日志组件
	 **/
	private static final MicroLogger LOGGER = new MicroLogger(ConcurrentController.class);

	@Autowired
	private ConcurrentLock lock;

	/**
	 * 分布式锁测试
	 **/
	@GetMapping("/concurrentLock1")
	public Map<String, Object> concurrentLock1() {
		final String methodName = "concurrentLock";
		LOGGER.enter(methodName, "分布式锁测试[start]");

		String lockVal = lock.key("key1").timeOut(30).execute(() -> {

			lock.key("key2").timeOut(5).execute(() -> {
				return "key2";
			});

			lock.key("key3").timeOut(2).execute(() -> {
				return "key3";
			});

			return "分布式锁";
		});

		System.out.println("锁: " + lockVal);
		LOGGER.exit(methodName, "分布式锁测试[end]");
		return Response.SUCCESS.newBuilder().toResult();
	}

	/**
	 * 分布式锁测试
	 **/
	@GetMapping("/concurrentLock2")
	public Map<String, Object> concurrentLock2() {
		final String methodName = "concurrentLock";
		LOGGER.enter(methodName, "分布式锁测试2[start]");

		String val = lock.key("key3").timeOut(2).execute(() -> {
			return "第二把锁";
		});
		System.out.println("val---> " + val);

		LOGGER.exit(methodName, "分布式锁测试2[end]");
		return Response.SUCCESS.newBuilder().toResult();
	}
}
