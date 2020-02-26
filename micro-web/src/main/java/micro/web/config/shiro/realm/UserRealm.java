package micro.web.config.shiro.realm;

import java.util.Optional;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import micro.bean.po.User;
import micro.service.demo.DemoService;
import micro.web.config.shiro.JwtToken;

/**
 * 用户权限控制
 * 
 * @author gewx
 **/
public final class UserRealm extends AuthorizingRealm {

	@Autowired
	private DemoService demoService;

	/**
	 * 获取授权信息
	 * 
	 * @author gewx
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRole("admin");
		simpleAuthorInfo.addStringPermission("write");
		System.out.println("权限控制---->");
		return simpleAuthorInfo;
	}

	/**
	 * 获取登录认证权限信息
	 * 
	 * @author gewx
	 **/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		// 登录用户名
		String token = (String) authToken.getPrincipal();

		/*
		User user = demoService.getUserById(userName);
		if (user == null) {
			return null;
		}
		*/
		// 创建新token
		User user = new User();
		user.setId("212");
		user.setUserName("geweixin");
		
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, token,
				user.getUserName());
		return authcInfo;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}
}