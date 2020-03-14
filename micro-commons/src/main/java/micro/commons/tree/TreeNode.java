package micro.commons.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 树状结构叶子节点
 * 
 * @author gewx
 **/
@Getter
@Setter
@ToString
public final class TreeNode implements Serializable {

	private static final long serialVersionUID = 3564872935020099986L;

	/**
	 * 菜单Id
	 **/
	private String menuId;

	/**
	 * 菜单名称
	 **/
	private String menuName;

	/**
	 * 菜单类型
	 **/
	private String menuType;

	/**
	 * 菜单编码
	 **/
	private String menuCode;

	/**
	 * 菜单URL
	 **/
	private String url;

	/**
	 * 菜单父级Id
	 **/
	public String parentId;

	/**
	 * 排序
	 **/
	private Integer sortNum;

	/**
	 * 子菜单
	 **/
	private List<TreeNode> children = new ArrayList<>();

	public TreeNode(String menuId, String menuName, String menuType, String menuCode, String url, String parentId,
			Integer sortNum) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuType = menuType;
		this.menuCode = menuCode;
		this.url = url;
		this.parentId = parentId;
		this.sortNum = sortNum;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TreeNode otherObject = (TreeNode) obj;
		if (getClass() != otherObject.getClass()) {
			return false;
		}

		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.menuId, otherObject.getMenuId());
		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		return this.menuId.hashCode() * 31;
	}
}
