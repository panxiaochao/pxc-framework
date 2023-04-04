package io.github.panxiaochao.file.storage.utils;

import io.github.panxiaochao.common.utils.StrUtil;
import io.github.panxiaochao.common.utils.StringPoolUtil;

/**
 * {@code FileNameUtil}
 * <p> description: 获取文件后缀
 *
 * @author Lypxc
 * @since 2023-03-10
 */
public class FileNameUtil {

    /**
     * 类Unix路径分隔符
     */
    public static final char UNIX_SEPARATOR = '/';
    /**
     * Windows路径分隔符
     */
    public static final char WINDOWS_SEPARATOR = '\\';

    /**
     * 特殊后缀
     */
    private static final CharSequence[] SPECIAL_SUFFIX = {"tar.bz2", "tar.Z", "tar.gz", "tar.xz"};

    public static String getSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(StringPoolUtil.DOT);
        if (index == -1) {
            return StringPoolUtil.EMPTY;
        } else {
            int secondToLastIndex = fileName.substring(0, index).lastIndexOf(StringPoolUtil.DOT);
            String substr = fileName.substring(secondToLastIndex == -1 ? index : secondToLastIndex + 1);
            if (StrUtil.containsAny(substr, SPECIAL_SUFFIX)) {
                return substr;
            }

            String ext = fileName.substring(index + 1);
            return StrUtil.containsAny(ext, UNIX_SEPARATOR, WINDOWS_SEPARATOR) ? StringPoolUtil.EMPTY : ext;
        }
    }
}
