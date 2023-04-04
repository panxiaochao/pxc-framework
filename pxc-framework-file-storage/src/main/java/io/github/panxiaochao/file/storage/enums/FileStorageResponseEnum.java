package io.github.panxiaochao.file.storage.enums;

import io.github.panxiaochao.common.ienums.IResponseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code FileStorageResponseEnum}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
@Getter
@RequiredArgsConstructor
public enum FileStorageResponseEnum implements IResponseEnum<Integer> {
    /**
     * 文件上传报错
     */
    FILE_STORAGE_UPLOAD_ERROR(50001, "文件上传报错"),
    /**
     * 文件存储报错
     */
    FILE_STORAGE_SAVE_ERROR(50002, "文件存储报错"),
    /**
     * 文件未找到
     */
    FILE_STORAGE_NOT_FOUND(50009, "文件未找到");

    private final Integer code;

    private final String message;

    /**
     * 根据code获取描述
     *
     * @param code 码值
     * @return 返回信息
     */
    @Override
    public String ofCode(Integer code) {
        for (FileStorageResponseEnum value : FileStorageResponseEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMessage();
            }
        }
        return null;
    }
}
