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

import java.lang.reflect.Array;

/**
 * {@code ArrayUtil}
 * <p>
 * description:
 *
 * @author Lypxc
 * @since 2023-03-10
 */
public class ArrayUtil {

  /**
   * An empty immutable {@code char} array.
   */
  public static final char[] EMPTY_CHAR_ARRAY = new char[0];

  /**
   * <p>
   * Checks if an array of primitive chars is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(final char[] array) {
    return getLength(array) == 0;
  }

  /**
   * <p>
   * Checks if an array of Objects is empty or {@code null}.
   *
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   * @since 2.1
   */
  public static boolean isEmpty(final Object[] array) {
    return getLength(array) == 0;
  }

  /**
   * 数组是否为空<br> 此方法会匹配单一对象，如果此对象为{@code null}则返回true<br> 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回false<br>
   * 如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
   *
   * @param array 数组
   * @return 是否为空
   */
  public static boolean isEmpty(Object array) {
    if (array != null) {
      if (isArray(array)) {
        return 0 == Array.getLength(array);
      }
      return false;
    }
    return true;
  }

  /**
   * 对象是否为数组对象
   *
   * @param obj 对象
   * @return 是否为数组对象，如果为{@code null} 返回false
   */
  public static boolean isArray(Object obj) {
    return null != obj && obj.getClass().isArray();
  }

  /**
   * <p>
   * Returns the length of the specified array. This method can deal with {@code Object} arrays and
   * with primitive arrays.
   *
   * <p>
   * If the input array is {@code null}, {@code 0} is returned.
   *
   * <pre>
   * ArrayUtil.getLength(null)            = 0
   * ArrayUtil.getLength([])              = 0
   * ArrayUtil.getLength([null])          = 1
   * ArrayUtil.getLength([true, false])   = 2
   * ArrayUtil.getLength([1, 2, 3])       = 3
   * ArrayUtil.getLength(["a", "b", "c"]) = 3
   * </pre>
   *
   * @param array the array to retrieve the length from, may be null
   * @return The length of the array, or {@code 0} if the array is {@code null}
   */
  public static int getLength(final Object array) {
    if (array == null) {
      return 0;
    }
    return Array.getLength(array);
  }

  /**
   * 数组是否为非空
   *
   * @param <T>   数组元素类型
   * @param array 数组
   * @return 是否为非空
   */
  public static <T> boolean isNotEmpty(T[] array) {
    return (null != array && array.length != 0);
  }

  /**
   * 数组是否为非空<br> 此方法会匹配单一对象，如果此对象为{@code null}则返回false<br> 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回true<br>
   * 如果此对象为数组对象，数组长度大于0情况下返回true，否则返回false
   *
   * @param array 数组
   * @return 是否为非空
   */
  public static boolean isNotEmpty(Object array) {
    return !isEmpty(array);
  }

  /**
   * <p>
   * Shallow clones an array returning a typecast result and handling
   *
   * @param <T>   the component type of the array
   * @param array the array to shallow clone, may be {@code null}
   * @return the cloned array, {@code null} if {@code null} input
   */
  public static <T> T[] clone(final T[] array) {
    if (array == null) {
      return null;
    }
    return array.clone();
  }

}
