/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.operate.log.annotation;

import java.lang.annotation.*;

/**
 * {@code OperateLog}
 * <p> description: 操作日志注解
 *
 * @author Lypxc
 * @since 2023-06-12
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    /**
     * The name of this OperateLog.
     *
     * @return the name of this OperateLog
     */
    String name() default "";

    /**
     * A description of the operate log.
     *
     * @return the operate log description
     **/
    String description() default "";

    /**
     * A pattern that the value SpElExpression. Ignored if the value is an empty string.
     *
     * @return the pattern of this SpElExpression
     **/
    String pattern() default "";

    /**
     * 业务类型
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类型
     */
    OperatorUserType operatorUserType() default OperatorUserType.WEB;

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
