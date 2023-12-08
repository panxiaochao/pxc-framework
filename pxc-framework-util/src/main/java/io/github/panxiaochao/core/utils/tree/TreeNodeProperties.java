package io.github.panxiaochao.core.utils.tree;

import lombok.Getter;

import java.io.Serializable;

/**
 * <p>
 * 树节点属性
 * </p>
 *
 * @author Lypxc
 * @since 2023-12-06
 */
@Getter
public class TreeNodeProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	public static TreeNodeProperties DEFAULT_PROPERTIES = new TreeNodeProperties();

	private String idKey = "id";

	private String parentIdKey = "parentId";

	private String childrenKey = "children";

	private String weightKey = "weight";

	private String labelKey = "name";

	/**
	 * 设置输出idKey别名
	 * @param idKey 别名id的key
	 * @return this
	 */
	public TreeNodeProperties idKey(String idKey) {
		this.idKey = idKey;
		return this;
	}

	/**
	 * 设置输出parentIdKey别名
	 * @param parentIdKey 别名parentId的key
	 * @return this
	 */
	public TreeNodeProperties parentIdKey(String parentIdKey) {
		this.parentIdKey = parentIdKey;
		return this;
	}

	/**
	 * 设置输出childrenKey别名
	 * @param childrenKey 别名children的key
	 * @return this
	 */
	public TreeNodeProperties childrenKey(String childrenKey) {
		this.childrenKey = childrenKey;
		return this;
	}

	/**
	 * 设置weightKey别名
	 * @param weightKey 别名weight的key
	 * @return this
	 */
	public TreeNodeProperties setWeightKey(String weightKey) {
		this.weightKey = weightKey;
		return this;
	}

	/**
	 * 设置labelKey别名
	 * @param labelKey 别名label的key
	 * @return this
	 */
	public TreeNodeProperties setLabelKey(String labelKey) {
		this.labelKey = labelKey;
		return this;
	}

}
