package io.github.panxiaochao.file.storage.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code PlatformEnum}
 * <p> description: 存储平台
 *
 * @author Lypxc
 * @since 2023-03-15
 */
@Getter
@RequiredArgsConstructor
public enum PlatformEnum {
    /**
     * Windows
     */
    WINDOWS("Windows"),
    /**
     * Linux
     */
    LINUX("Linux"),
    /**
     * macOS
     */
    MACOS("macOS");

    private final String label;
}
