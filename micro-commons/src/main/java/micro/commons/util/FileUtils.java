package micro.commons.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import micro.commons.annotation.ThreadSafe;

/**
 * 文件操作工具类
 * 
 * @author gewx
 **/
@ThreadSafe
public final class FileUtils {

	private static final Pattern pattern = Pattern.compile("accNo=([A-Za-z]{1,30}),");

	/**
	 * 提取文件当中的匹配字符串
	 * 
	 * @author gewx
	 * @param path     文件路径
	 * @param pattern  匹配正则表达式
	 * @param groupNum 正则分组
	 * @throws IOException
	 * @return 提取结果集
	 **/
	public static List<String> extract(String path, Pattern pattern, int groupNum) throws IOException {
		List<String> dataList = new ArrayList<>();
		Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(val -> {
			Matcher matcher = pattern.matcher(val);
			while (matcher.find()) {
				dataList.add(matcher.group(groupNum));
			}
		});
		return dataList;
	}

	public static void main(String[] args) throws IOException {
		FileUtils.extract("c:\\logs.html", pattern, 1).stream().forEach(val -> {
			System.out.println(val);
		});
	}
}
