// /*
//  * Copyright © 2022-2023 Lypxc (545685602@qq.com)
//  *
//  * Licensed under the Apache License, Version 2.0 (the "License");
//  * you may not use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  *     http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */
// package io.github.panxiaochao.redis.annotation;
//
// import java.lang.annotation.*;
// import java.util.concurrent.TimeUnit;
//
// /**
//  * {@code AccessRateLimit}
//  * <p>Redis 分布式锁注解
//  * <pre>
//  *  ElementType.TYPE：能修饰类、接口或枚举类型
//  *  ElementType.FIELD：能修饰成员变量
//  *  ElementType.METHOD：能修饰方法
//  * </pre>
//  *
//  * @author Lypxc
//  * @since 2023-05-29
//  */
// @Documented
// @Target({ElementType.METHOD})
// @Retention(RetentionPolicy.RUNTIME)
// public @interface RedisLock {
//
//     /**
//      * redis 锁名
//      */
//     String lockName() default "redisson_lock:";
//
//     /**
//      * redis key 前缀
//      */
//     String key() default "";
//
//     /**
//      * 等待锁时间, 默认0秒
//      */
//     long waitTime() default 0;
//
//     /**
//      * 释放时间, 默认5秒
//      */
//     long leaseTime() default 5000;
//
//     /**
//      * 时间单位, 默认毫秒
//      */
//     TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
// }
