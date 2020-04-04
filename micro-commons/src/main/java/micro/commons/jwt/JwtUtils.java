package micro.commons.jwt;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;

import micro.commons.annotation.ThreadSafe;
import micro.commons.util.JasyptUtils;
import micro.commons.util.SpringUtils;

/**
 * Jwt工具类
 * 
 * @author gewx
 **/
@ThreadSafe
public final class JwtUtils {

	/**
	 * 解析token
	 * 
	 * @author gewx
	 * @param token
	 * @return jwtBean
	 **/
	public static Jwt.JwtBean parseToken(String token) {
		String strJsonToken = JasyptUtils.decryptByBase64(token);
		return JSONObject.parseObject(strJsonToken, Jwt.JwtBean.class);
	}

	/**
	 * 本地token鉴权
	 * 
	 * @author gewx
	 * @param token
	 * @return true 验证通过, false 验证不通过,token已过期
	 **/
	public static boolean verifyToken(String token) {
		Jwt.JwtBean bean = parseToken(token);
		DateTime thisDate = new DateTime();
		DateTime tokenExpiresDate = new DateTime(bean.getExpiresDate());
		if (thisDate.isAfter(tokenExpiresDate)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 本地token鉴权
	 * 
	 * @author gewx
	 * @param bean token对象
	 * @return true 验证通过, false 验证不通过,token已过期
	 **/
	public static boolean verifyToken(Jwt.JwtBean bean) {
		DateTime thisDate = new DateTime();
		DateTime tokenExpiresDate = new DateTime(bean.getExpiresDate());
		if (thisDate.isAfter(tokenExpiresDate)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 远程token鉴权
	 * 
	 * @author gewx
	 * @param token
	 * @return true 验证通过, false 验证不通过
	 **/
	public static boolean remoteVerifyToken(String token) {
		Jwt.JwtBean bean = parseToken(token);
		RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");
		return redisTemplate.opsForValue().setIfAbsent(token, token, bean.getExpires() + 2, TimeUnit.MINUTES);
	}
}
