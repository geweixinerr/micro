package micro.commons.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 树状结构顶层元素
 * 
 * @author gewx
 **/
public class Node implements Serializable {

	private static final long serialVersionUID = -4405388942387953237L;

	/**
	 * Id
	 **/
	@Getter
	@Setter
	protected String id;

	/**
	 * 父级Id
	 **/
	@Getter
	@Setter
	protected String parentId;

	/**
	 * 排序
	 **/
	@Getter
	@Setter
	protected Integer sortNum;

	/**
	 * 关联子节点
	 **/
	@Getter
	@Setter
	protected List<Node> children = new ArrayList<>();
	
	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Node otherObject = (Node) obj;
		if (getClass() != otherObject.getClass()) {
			return false;
		}

		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.id, otherObject.id);
		return builder.isEquals();
	}
	
	@Override
	public final int hashCode() {
		return this.id.hashCode() * 31;
	}
}
