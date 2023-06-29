package io.github.panxiaochao.repeatsubmit.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * {@code RepeatSubmitLimiter}
 * <p> 限制重复提交注解
 *
 * @author Lypxc
 * @since 2023-06-29
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmitLimiter {

    /**
     * 提交间隔时间, 小于此时间间隔属于重复提交, 默认毫秒
     */
    long interval() default 5000;

    /**
     * 时间单位格式, 默认毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 自定义请求头, 比如 Header 中的 AccessToken 值作为唯一值
     */
    // String headerName() default "";
}
