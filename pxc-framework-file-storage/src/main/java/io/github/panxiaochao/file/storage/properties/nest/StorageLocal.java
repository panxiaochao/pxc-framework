package io.github.panxiaochao.file.storage.properties.nest;

import io.github.panxiaochao.file.storage.enums.PlatformEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code StorageLoca}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-15
 */
@Getter
@Setter
public class StorageLocal {
    /**
     * 存储平台, 默认Windows
     */
    private PlatformEnum platform = PlatformEnum.WINDOWS;
}
