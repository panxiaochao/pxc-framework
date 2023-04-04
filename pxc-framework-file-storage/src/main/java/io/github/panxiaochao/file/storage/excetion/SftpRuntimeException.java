package io.github.panxiaochao.file.storage.excetion;

import io.github.panxiaochao.common.exception.BaseRuntimeException;
import io.github.panxiaochao.common.ienums.IResponseEnum;

/**
 * {@code FileStorageRuntimeException}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-13
 */
public class SftpRuntimeException extends BaseRuntimeException {
    public SftpRuntimeException() {
        super();
    }

    public SftpRuntimeException(IResponseEnum<Integer> responseEnum) {
        super(responseEnum);
    }

    public SftpRuntimeException(IResponseEnum<Integer> responseEnum, Throwable cause) {
        super(responseEnum, cause);
    }

    public SftpRuntimeException(IResponseEnum<Integer> responseEnum, String message) {
        super(responseEnum, message);
    }

    public SftpRuntimeException(IResponseEnum<Integer> responseEnum, String message, Throwable cause) {
        super(responseEnum, message, cause);
    }

    public SftpRuntimeException(IResponseEnum<Integer> responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public SftpRuntimeException(IResponseEnum<Integer> responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
