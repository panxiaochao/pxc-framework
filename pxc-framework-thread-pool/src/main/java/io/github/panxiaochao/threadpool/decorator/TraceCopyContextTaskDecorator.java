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
package io.github.panxiaochao.threadpool.decorator;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.Objects;

/**
 * {@code TraceCopyContextTaskDecorator}
 * <p> description: 多线程自定义传递上下文
 *
 * @author Lypxc
 * @since 2023-01-13
 */
public class TraceCopyContextTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Map<String, String> map = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (Objects.nonNull(requestAttributes)) {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                }
                if (Objects.nonNull(map)) {
                    MDC.setContextMap(map);
                }
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
                MDC.clear();
            }
        };
    }
}
