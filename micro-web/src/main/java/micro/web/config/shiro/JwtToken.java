package micro.web.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import micro.bean.po.User;

/**
 * JwtToken 数据对象
 * 
 * @author gewx
 **/
@Setter
@Getter
@ToString
public final class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 2382305836675887108L;

	/**
	 * 用户名,唯一
	 **/
	private final String userName;

	/**
	 * 认证Token
	 **/
	private String token;

	/**
	 * 登录用户信息
	 **/
	private User user;

	public JwtToken(String userName) {
		this.userName = userName;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
