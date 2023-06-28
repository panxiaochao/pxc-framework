package io.github.panxiaochao.ratelimiter.annotation;

import java.lang.annotation.*;

/**
 * {@code RateLimiter}
 * <p>防刷、限流注解, 应用于字段、类、方法
 * <pre>
 *  ElementType.TYPE：能修饰类、接口或枚举类型
 *  ElementType.FIELD：能修饰成员变量
 *  ElementType.METHOD：能修饰方法
 * </pre>
 * <p>限流的思路:
 * <pre>
 * 1、 通过[请求路径或者方法名:访问ip]的作为key，[访问次数]为value的方式对某一请求进行唯一标识
 * 2、 每次访问的时候判断key是否存在，是否count超过了限制的访问次数
 * 3、 若访问超出限制，则返回限制报错信息
 * </pre>
 *
 * @author Lypxc
 * @since 2023-06-28
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流Key, 支持 Spring EL 表达式, 例如 #{id}, #{user.id}
     */
    String key() default "";

    /**
     * 指定second时间内, API最大请求次数
     */
    int maxCount() default 10;

    /**
     * 限定时间范围, 单位秒
     */
    int limitSecond() default 60;
}
