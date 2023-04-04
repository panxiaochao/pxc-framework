package io.github.panxiaochao.common.utils;

import java.util.UUID;

/**
 * {@code UuidUtil}
 * <p> description: UUID 生成
 *
 * @author Lypxc
 * @since 2023-03-16
 */
public class UuidUtil {

    /**
     * 获取原生UUID
     *
     * @return return UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取原生UUID，去除-的简化UUID
     *
     * @return return simple UUID
     */
    public static String getSimpleUUID() {
        return getUUID().replaceAll("-", "");
    }
}
