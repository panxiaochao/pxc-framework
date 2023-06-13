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
package io.github.panxiaochao.operate.log.aop.afterreturn;

import io.github.panxiaochao.operate.log.domain.OperateLogDomain;
import io.github.panxiaochao.operate.log.utils.OperateLogUtil;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * {@code OperateLogAfterReturningAdvice}
 * <p> description: aop method returning
 *
 * @author Lypxc
 * @since 2023-06-12
 */
public class OperateLogAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) {
        OperateLogDomain operateLogDomain = new OperateLogDomain();
        // 设置方法名称
        String className = target.getClass().getName();
        String methodName = method.getName();
        operateLogDomain.setClassMethod(className + "." + methodName + "()");
        // 处理其他字段
        OperateLogUtil.handleOperateLog(returnValue, method, args, operateLogDomain, null);
    }
}
