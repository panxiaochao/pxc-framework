package io.github.panxiaochao.common.utils;

import java.lang.reflect.Array;

/**
 * {@code ArrayUtils}
 * <p> description:
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
     * <p>Checks if an array of primitive chars is empty or {@code null}.
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(final char[] array) {
        return getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of Objects is empty or {@code null}.
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    /**
     * <p>Returns the length of the specified array.
     * This method can deal with {@code Object} arrays and with primitive arrays.
     *
     * <p>If the input array is {@code null}, {@code 0} is returned.
     *
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
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
}
