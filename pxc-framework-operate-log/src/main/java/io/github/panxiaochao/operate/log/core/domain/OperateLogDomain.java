package io.github.panxiaochao.operate.log.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code OperateLogDomain}
 * <p> 操作日志 domain
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@Getter
@Setter
@ToString
public class OperateLogDomain implements Serializable {

    private static final long serialVersionUID = -8831737354114961499L;

    /**
     * 名称
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 操作人员类型
     */
    private Integer operateUsertype;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求类型
     */
    private String requestContentType;

    /**
     * 请求Ip
     */
    private String ip;

    /**
     * 请求类名
     */
    private String className;

    /**
     * 请求类方法
     */
    private String classMethod;

    /**
     * GET - 请求参数
     */
    private String requestParam;

    /**
     * POST - 请求参数
     */
    private String requestBody;

    /**
     * 返回内容
     */
    private Object responseData;

    /**
     * 执行耗时
     */
    private long costTime;

    /**
     * 请求时间
     */
    private LocalDateTime requestDateTime;

    /**
     * 是否成功 1=成功, 0=失败
     */
    private Integer code;

    /**
     * 错误原因
     */
    private String errorMessage;
}
