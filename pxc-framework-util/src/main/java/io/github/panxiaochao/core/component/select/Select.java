package io.github.panxiaochao.core.component.select;

import io.github.panxiaochao.core.utils.ObjectUtil;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.function.Predicate;

/**
 * <p>
 * 下拉菜单 结构类
 * </p>
 *
 * @author Lypxc
 * @since 2023-12-20
 */
@Getter
public class Select<T> extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private final SelectOptionProperties selectOptionProperties;

	/**
	 * 无参构造
	 */
	public Select(SelectOptionProperties properties) {
		this.selectOptionProperties = ObjectUtil.getIfNull(properties, SelectOptionProperties.builder());
	}

	public boolean getDisabled() {
		return (boolean) this.get(selectOptionProperties.getDisabled());
	}

	public Select<T> setDisabled(boolean disabled) {
		this.put(selectOptionProperties.getDisabled(), disabled);
		return this;
	}

	@SuppressWarnings("unchecked")
	public T getKey() {
		return (T) this.get(selectOptionProperties.getKey());
	}

	public Select<T> setKey(T key) {
		this.put(selectOptionProperties.getKey(), key);
		return this;
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) this.get(selectOptionProperties.getValue());
	}

	public Select<T> setValue(T value) {
		this.put(selectOptionProperties.getValue(), value);
		return this;
	}

	public CharSequence getTitle() {
		return (CharSequence) this.get(selectOptionProperties.getTitle());
	}

	public Select<T> setTitle(CharSequence title) {
		this.put(selectOptionProperties.getTitle(), title);
		return this;
	}

	public Comparable<?> getWeight() {
		return (Comparable<?>) this.get(selectOptionProperties.getWeight());
	}

	public Select<T> setWeight(Comparable<?> weight) {
		this.put(selectOptionProperties.getWeight(), weight);
		return this;
	}

	/**
	 * 过滤并生成新的下拉菜单<br>
	 * 通过{@link Predicate}指定的过滤规则，本节点或子节点满足过滤条件，则保留当前节点，否则抛弃节点及其子节点
	 * @param predicate 节点过滤规则函数，只需处理本级节点本身即可
	 * @return 过滤后的节点，{@code null} 表示不满足过滤要求，丢弃之
	 * @see #filter(Predicate)
	 */
	public Select<T> filterNew(Predicate<Select<T>> predicate) {
		return cloneTree().filter(predicate);
	}

	/**
	 * 过滤当前下拉菜单，注意此方法会修改当前下拉菜单
	 * @param predicate 节点过滤规则函数，只需处理本级节点本身即可
	 * @return 过滤后的节点，{@code null} 表示不满足过滤要求，丢弃之
	 * @see #filterNew(Predicate)
	 */
	public Select<T> filter(Predicate<Select<T>> predicate) {
		if (predicate.test(this)) {
			// 本节点满足，则包括所有子节点都保留
			return this;
		}
		// 子节点都不符合过滤条件，检查本节点
		return null;
	}

	/**
	 * 扩展属性
	 * @param key 键
	 * @param value 扩展值
	 */
	public void putExtra(String key, Object value) {
		Assert.notNull(key, "Key must be not empty !");
		this.put(key, value);
	}

	/**
	 * 递归克隆当前节点（即克隆整个树，保留字段值）<br>
	 * 注意，此方法只会克隆节点，节点属性如果是引用类型，不会克隆
	 * @return 新的节点
	 */
	public Select<T> cloneTree() {
		return ObjectUtil.clone(this);
	}

}
