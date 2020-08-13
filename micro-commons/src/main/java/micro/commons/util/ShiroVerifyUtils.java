package micro.commons.util;

import org.apache.shiro.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Shiro权限验证类
 * 
 * @author gewx 2020.4.3
 **/
public final class ShiroVerifyUtils {

	private static final String WILDCARD_TOKEN = "*";

	private static final String PART_DIVIDER_TOKEN = ":";

	private static final String SUBPART_DIVIDER_TOKEN = ",";

	/**
	 * Shiro权限匹配验证
	 * 
	 * @author gewx
	 * @param myPermission  我的权限
	 * @param resourcePermission 资源权限
	 * @return boolean
	 **/
	public static boolean implies(String myPermission, String resourcePermission) {
		// init
		List<Set<String>> goalParts = initPermission(myPermission);
		List<Set<String>> otherParts = initPermission(resourcePermission);

		int i = 0;
		for (Set<String> otherPart : otherParts) {
			// If this permission has less parts than the other permission, everything after
			// the number of parts contained
			// in this permission is automatically implied, so return true
			if (goalParts.size() - 1 < i) {
				return true;
			} else {
				Set<String> part = goalParts.get(i);
				if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)) {
					return false;
				}
				i++;
			}
		}

		// If this permission has more parts than the other parts, only imply it if all
		// of the other parts are wildcards
		for (; i < goalParts.size(); i++) {
			Set<String> part = goalParts.get(i);
			if (!part.contains(WILDCARD_TOKEN)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 初始化权限许可列表
	 * 
	 * @author gewx
	 * @param wildCardString
	 * @return List<Set<String>> 权限许可列表
	 **/
	private static List<Set<String>> initPermission(String wildCardString) {
		List<Set<String>> parts = new ArrayList<Set<String>>();
		List<String> goalWildCardList = CollectionUtils.asList(wildCardString.split(PART_DIVIDER_TOKEN));

		for (String part : goalWildCardList) {
			Set<String> subparts = CollectionUtils.asSet(part.split(SUBPART_DIVIDER_TOKEN));
			if (subparts.isEmpty()) {
				throw new IllegalArgumentException(
						"Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
			}
			parts.add(subparts);
		}
		return parts;
	}

}
