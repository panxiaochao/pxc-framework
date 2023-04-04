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
public enum SftpResponseEnum implements IResponseEnum<Integer> {
    /**
     * Sftp创建报错
     */
    SFTP_CREATED_ERROR(60001, "Sftp创建报错"),
    /**
     * Sftp上传报错
     */
    SFTP_UPLOAD_ERROR(60002, "Sftp上传报错");

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
        for (SftpResponseEnum value : SftpResponseEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMessage();
            }
        }
        return null;
    }
}
