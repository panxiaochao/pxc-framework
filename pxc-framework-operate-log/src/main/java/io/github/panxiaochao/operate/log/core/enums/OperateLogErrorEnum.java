package io.github.panxiaochao.operate.log.core.enums;

import io.github.panxiaochao.core.ienums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code OperateLogErrorEnum}
 * <p> 操作日志错误枚举
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@Getter
@AllArgsConstructor
public enum OperateLogErrorEnum implements IEnum<Integer> {
    /**
     * 请配置操作日志处理类
     */
    OPERATE_LOG_ERROR(6061, "请配置操作日志处理类！");

    private final Integer code;

    private final String message;
}
