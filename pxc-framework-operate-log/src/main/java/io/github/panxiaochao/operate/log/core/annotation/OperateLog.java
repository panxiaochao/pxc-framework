package io.github.panxiaochao.operate.log.core.annotation;

import java.lang.annotation.*;

/**
 * {@code OperateLog}
 * <p> 操作日志注解
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    /**
     * 模块名
     */
    String title() default "";

    /**
     * 描述操作日志
     **/
    String description() default "";

    /**
     * 业务类型
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类型
     */
    OperatorUserType operatorUserType() default OperatorUserType.WEB;

    /**
     * 排除指定的请求参数名
     */
    String[] excludeParamNames() default {};

    /**
     * 是否保存请求的参数
     */
    boolean saveReqParams() default true;

    /**
     * 是否保存响应的参数
     */
    boolean saveResData() default true;

    /**
     * 业务类型
     */
    enum BusinessType {
        /**
         * 新增
         */
        INSERT,
        /**
         * 修改
         */
        UPDATE,
        /**
         * 删除
         */
        DELETE,
        /**
         * 授权
         */
        GRANT,
        /**
         * 导出
         */
        EXPORT,
        /**
         * 导入
         */
        IMPORT,
        /**
         * 强退
         */
        FORCE,
        /**
         * 生成代码
         */
        GENERATE_CODE,
        /**
         * 清空数据
         */
        CLEAN,
        /**
         * 其它
         */
        OTHER
    }

    /**
     * 操作人类型
     */
    enum OperatorUserType {
        /**
         * PC 用户
         */
        WEB,
        /**
         * PHONE 用户
         */
        MOBILE,
        /**
         * 其它
         */
        OTHER
    }
}
