/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.core.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * {@code MapUtil}
 * <p>
 * description: Map工具类
 *
 * @author Lypxc
 * @since 2022-11-28
 */
public class MapUtil {

  /**
   * 默认初始大小
   */
  public static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * 默认增长因子，当Map的size达到 容量*增长因子时，开始扩充Map
   */
  public static final float DEFAULT_LOAD_FACTOR = 0.75f;

  /**
   * 新建一个HashMap
   *
   * @param <K> Key类型
   * @param <V> Value类型
   * @return HashMap对象
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<>();
  }

  /**
   * 新建一个HashMap
   *
   * @param <K>  Key类型
   * @param <V>  Value类型
   * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
   * @return HashMap对象
   */
  public static <K, V> HashMap<K, V> newHashMap(int size) {
    return newHashMap(size, false);
  }

  /**
   * 新建一个HashMap
   *
   * @param <K>      Key类型
   * @param <V>      Value类型
   * @param size     初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
   * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
   * @return HashMap对象
   */
  public static <K, V> HashMap<K, V> newHashMap(int size, boolean isLinked) {
    final int initialCapacity = (int) (size / DEFAULT_LOAD_FACTOR) + 1;
    return isLinked ? new LinkedHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
  }

  /**
   * Null-safe check if the specified Dictionary is empty.
   *
   * <p>
   * Null returns true.
   *
   * @param map the collection to check, may be null
   * @return true if empty or null
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return (null == map || map.isEmpty());
  }

  /**
   * Null-safe check if the specified Dictionary is not empty.
   *
   * <p>
   * Null returns false.
   *
   * @param map the collection to check, may be null
   * @return true if non-null and non-empty
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }

  /**
   * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br> 空集合使用{@link Collections#emptyMap()}
   *
   * @param <K> 键类型
   * @param <V> 值类型
   * @param set 提供的集合，可能为null
   * @return 原集合，若为null返回空集合
   */
  public static <K, V> Map<K, V> emptyIfNull(Map<K, V> set) {
    return (null == set) ? Collections.emptyMap() : set;
  }

  /**
   * Put into map if value is not null.
   *
   * @param target target map
   * @param key    key
   * @param value  value
   */
  public static void putIfValNoNull(Map target, Object key, Object value) {
    Objects.requireNonNull(key, "key");
    if (value != null) {
      target.put(key, value);
    }
  }

  /**
   * Put into map if value is not empty.
   *
   * @param target target map
   * @param key    key
   * @param value  value
   */
  public static void putIfValNoEmpty(Map target, Object key, Object value) {
    Objects.requireNonNull(key, "key");
    if (value instanceof String) {
      if (!StringUtils.hasText((String) value)) {
        target.put(key, value);
      }
      return;
    }
    if (value instanceof Collection) {
      if (!CollectionUtils.isEmpty((Collection) value)) {
        target.put(key, value);
      }
      return;
    }
    if (value instanceof Map) {
      if (isNotEmpty((Map) value)) {
        target.put(key, value);
      }
      return;
    }
  }

  /**
   * ComputeIfAbsent lazy load. not thread safe
   *
   * @param target          target Map data.
   * @param key             map key.
   * @param mappingFunction function which is need to be executed.
   * @param param1          function's parameter value1.
   * @param param2          function's parameter value1.
   * @return V
   */
  public static <K, C, V, T> V computeIfAbsent(Map<K, V> target, K key,
      BiFunction<C, T, V> mappingFunction, C param1,
      T param2) {

    Objects.requireNonNull(target, "target");
    Objects.requireNonNull(key, "key");
    Objects.requireNonNull(mappingFunction, "mappingFunction");
    Objects.requireNonNull(param1, "param1");
    Objects.requireNonNull(param2, "param2");

    V val = target.get(key);
    if (val == null) {
      V ret = mappingFunction.apply(param1, param2);
      target.put(key, ret);
      return ret;
    }
    return val;
  }

  /**
   * 去掉Map中指定key的键值对，修改原Map
   *
   * @param <K>  Key类型
   * @param <V>  Value类型
   * @param map  Map
   * @param keys 键列表
   * @return 修改后的key
   */
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> removeAny(Map<K, V> map, final K... keys) {
    for (K key : keys) {
      map.remove(key);
    }
    return map;
  }

}
