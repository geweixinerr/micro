package micro.commons.util;

import java.util.function.Supplier;

import micro.commons.annotation.ThreadSafe;

/**
 * 延迟加载工具类
 * 
 * @author gewx
 **/
@ThreadSafe
public final class LazyUtils {

	/**
	 * 延迟加载
	 * 
	 * @author gewx
	 * @param object     执行对象
	 * @param lazyObject 延迟执行对象
	 * @return 数据域
	 **/
	public static <T> T get(Supplier<T> object, Supplier<T> lazyObject) {
		T val = object.get();
		if (val == null) {
			val = lazyObject.get();
		}
		return val;
	}
}
