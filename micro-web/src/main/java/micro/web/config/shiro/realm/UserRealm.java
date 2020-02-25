package micro.web.config.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 用户权限控制
 * 
 * @author gewx
 * **/
public class UserRealm extends AuthorizingRealm {
	
    /**
     * 获取授权信息
     * 
     * @author gewx
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        
        return simpleAuthorInfo;
    }

    /**
     * 获取登录认证权限信息
     * 
     * **/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(),"葛伟新");
		return authcInfo;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return true;
	}
}