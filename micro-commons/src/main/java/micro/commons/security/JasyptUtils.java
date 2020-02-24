package micro.commons.security;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;

/**
 * jasypt PBE基于口令加密
 * 
 * @author gewx
 **/
public final class JasyptUtils {

	// salt 随机盐
	private static final String myEncryptionPassword = "123456";

	private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

	private static final PooledPBEStringEncryptor ENCRYPTOR = new PooledPBEStringEncryptor();

	// init
	static {
		ENCRYPTOR.setPoolSize(CORE_SIZE);
		ENCRYPTOR.setPassword(myEncryptionPassword);
		ENCRYPTOR.setAlgorithm("PBEWithMD5AndTripleDES");
	}

	/**
	 * 加密
	 * 
	 * @author gewx
	 * @param val 加密字符串
	 * @return 返回加密后的字符串
	 **/
	public static String encrypt(String val) {
		return ENCRYPTOR.encrypt(val);
	}

	/**
	 * 加密
	 * 
	 * @author gewx
	 * @param val 加密字符串
	 * @return 返回加密后的字符串（BASE64）
	 **/
	public static String encryptToBase64(String val) {
		String encryptStr = encrypt(val);
		return Base64.encodeBase64URLSafeString(encryptStr.getBytes());
	}
	
	/**
	 * 解密
	 * 
	 * @author gewx
	 * @param val 解密字符串
	 * @return 返回解密后的字符串
	 **/
	public static String decrypt(String val) {
		return ENCRYPTOR.decrypt(val);
	}

	/**
	 * 解密
	 * 
	 * @author gewx
	 * @param val 解密字符串(BASE64编码后的字符串)
	 * @return 返回解密后的字符串
	 **/
	public static String decryptByBase64(String val) {
		String decryptStr = new String(Base64.decodeBase64(val));
		return ENCRYPTOR.decrypt(decryptStr);
	}
}
