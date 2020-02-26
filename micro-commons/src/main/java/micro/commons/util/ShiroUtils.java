package micro.commons.util;

import org.apache.shiro.SecurityUtils;

import micro.bean.po.User;
import micro.commons.annotation.ThreadSafe;

/**
 * Shiro 工具类
 * 
 * @author gewx
 **/
@ThreadSafe
public final class ShiroUtils {

	/**
	 * 获取登录用户对象
	 * 
	 * @author gewx
	 * 
	 * @return 登录用户对象
	 **/
	public static User getUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}
}
