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
package io.github.panxiaochao.operate.log.aop.before;

import io.github.panxiaochao.operate.log.context.MethodContext;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * {@code OperateLogMethodBeforeAdvice}
 * <p> description: aop before method
 *
 * @author Lypxc
 * @since 2023-06-12
 */
public class OperateLogMethodBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, @Nullable Object target) {
        // 设置请求方法执行开始时间
        MethodContext.setMethodCostTime(System.currentTimeMillis());
    }
}
