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
package io.github.panxiaochao.operate.log.aop.throwing;

import io.github.panxiaochao.operate.log.domain.OperateLogDomain;
import io.github.panxiaochao.operate.log.utils.OperateLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * {@code OperateLogThrowing}
 * <p> description: aop throws advice
 *
 * @author Lypxc
 * @since 2023-06-12
 */
public class OperateLogThrowing implements ThrowsAdvice {

    /**
     * LOGGER OperateLogThrowing.class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogThrowing.class);

    /**
     * 需要自定义实现 afterThrowing
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        LOGGER.error("AfterThrowing", ex);
        OperateLogDomain operateLogDomain = new OperateLogDomain();
        // 设置方法名称
        String className = target.getClass().getName();
        String methodName = method.getName();
        operateLogDomain.setClassMethod(className + "." + methodName + "()");
        // 处理其他字段
        OperateLogUtil.handleOperateLog(null, method, args, operateLogDomain, ex);
    }
}
