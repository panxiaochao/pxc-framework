package io.github.panxiaochao.common.utils;

/**
 * {@code CharSequenceUtil}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-10
 */
public class CharSequenceUtil {

    /**
     * Converts the given CharSequence to a char[].
     *
     * @param source the {@code CharSequence} to be processed.
     * @return the resulting char array, never null.
     */
    public static char[] toCharArray(final CharSequence source) {
        final int len = StrUtil.length(source);
        if (len == 0) {
            return ArrayUtil.EMPTY_CHAR_ARRAY;
        }
        if (source instanceof String) {
            return ((String) source).toCharArray();
        }
        final char[] array = new char[len];
        for (int i = 0; i < len; i++) {
            array[i] = source.charAt(i);
        }
        return array;
    }
}
