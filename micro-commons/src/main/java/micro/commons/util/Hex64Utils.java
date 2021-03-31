package micro.commons.util;

import micro.commons.annotation.ThreadSafe;

/**
 * 10进制/64进制互转工具
 * 
 * @author gewx
 **/
@ThreadSafe
public final class Hex64Utils {

	/**
	 * 初始化62进制数据，索引位置代表字符的数值比如A代表10,z代表61
	 */
	private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 进制
	 **/
	private static final int SCALE = 62;

	/**
	 * 将10进制数字转为62进制
	 *
	 * @author gewx
	 * @param val Long整型
	 * @return 62进制字符串
	 */
	public static String encode(long val) {
		StringBuilder sb = new StringBuilder();
		int remainder = 0;
		while (val > SCALE - 1) {
			/**
			 * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
			 */
			remainder = Long.valueOf(val % SCALE).intValue();
			sb.append(CHARS.charAt(remainder));

			val = val / SCALE;
		}

		sb.append(CHARS.charAt(Long.valueOf(val).intValue()));
		String value = sb.reverse().toString();
		return value;
	}

	/**
	 * 62进制字符串转为10进制
	 *
	 * @author gewx
	 * @param val 编码后的62进制字符串
	 * @return 10进制字符串
	 */
	public static long decode(String val) {
		long num = 0;
		int index = 0;
		for (int i = 0; i < val.length(); i++) {
			/**
			 * 查找字符的索引位置
			 */
			index = CHARS.indexOf(val.charAt(i));
			/**
			 * 索引位置代表字符的数值
			 */
			num += (long) (index * (Math.pow(SCALE, val.length() - i - 1)));
		}

		return num;
	}
}
