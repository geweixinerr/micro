package micro.commons.util;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;

import micro.commons.annotation.ThreadSafe;
import micro.commons.jwt.Jwt;

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
	 * 验证token
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
}
