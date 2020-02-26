package micro.web.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	 * 认证Token
	 **/
	private final String token;

	public JwtToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
