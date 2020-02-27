package micro.commons.jwt;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import micro.commons.util.JasyptUtils;
import micro.commons.util.Assert;

/**
 * 基于JWT概念设计实现, 基于PBE混淆加密
 * 
 * @author gewx
 **/
public final class Jwt {

	private final JwtBean bean;

	public Jwt(JwtBean bean) {
		this.bean = bean;
	}

	/**
	 * 创建
	 **/
	public static JwtBuilder create() {
		return new JwtBuilder();
	}

	/**
	 * 构建者
	 **/
	public static class JwtBuilder {

		/**
		 * 用户名
		 **/
		private String userName;

		/**
		 * Token过期时间,单位:分
		 **/
		private int expires;

		/**
		 * 其他附属数据
		 **/
		private Map<String, Object> kv;

		public JwtBuilder() {
			this.kv = new HashMap<>(8);
		}

		public JwtBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public JwtBuilder setExpires(int expires) {
			this.expires = expires;
			return this;
		}

		public JwtBuilder addKv(String key, String value) {
			kv.put(key, value);
			return this;
		}

		public JwtBuilder addAllKv(Map<String, Object> allKv) {
			kv.putAll(allKv);
			return this;
		}

		public Jwt build() {
			Assert.isNotBlank(userName, "userName is empty!");
			Assert.neq(this.expires, 0, "expires is ZERO!");
			Assert.gt(this.expires, 0, "expires lt is ZERO!");
			
			DateTime expiresDate = new DateTime().plusMinutes(expires);
			JwtBean bean = new JwtBean();
			bean.setUserName(userName);
			bean.setExpires(expires);
			bean.setExpiresDate(expiresDate.toDate().getTime());
			bean.setKv(kv);
			return new Jwt(bean);
		}
	}

	/**
	 * Jwt消息Bean
	 **/
	@Getter
	@Setter
	public static class JwtBean {

		/**
		 * 用户名
		 **/
		private String userName;

		/**
		 * Token过期时间,单位:分
		 **/
		private int expires;

		/**
		 * token失效日期
		 **/
		private long expiresDate;

		/**
		 * 其他附属数据
		 **/
		private Map<String, Object> kv;
	}

	/**
	 * 签名生成Token
	 * 
	 * @author gewx
	 * @return 签名token
	 **/
	public String sign() {
		String token = JasyptUtils.encryptToBase64(JSONObject.toJSONString(bean));
		return token;
	}
}
