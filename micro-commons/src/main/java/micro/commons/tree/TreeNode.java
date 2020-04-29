package micro.commons.tree;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 实体映射叶子节点
 * 
 * @author gewx
 **/
@ToString
public final class TreeNode extends Node {

	private static final long serialVersionUID = 3564872935020099986L;

	/**
	 * 菜单名称
	 **/
	@Getter
	@Setter
	private String menuName;

	/**
	 * 菜单类型
	 **/
	@Getter
	@Setter
	private String menuType;

	/**
	 * 菜单编码
	 **/
	@Getter
	@Setter
	private String menuCode;

	/**
	 * 菜单URL
	 **/
	@Getter
	@Setter
	private String url;

	public TreeNode(String id, String menuName, String menuType, String menuCode, String url, String parentId,
			Integer sortNum) {
		this.id = id;
		this.parentId = parentId;
		this.sortNum = sortNum;
		this.menuName = menuName;
		this.menuType = menuType;
		this.menuCode = menuCode;
		this.url = url;
	}
}
