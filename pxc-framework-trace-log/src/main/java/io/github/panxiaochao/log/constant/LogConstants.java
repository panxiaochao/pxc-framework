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
package io.github.panxiaochao.log.constant;

/**
 * {@code LogConstants}
 * <p> description: 常量
 *
 * @author Lypxc
 * @since 2023-01-10
 */
public interface LogConstants {

    String APPLICATION_NAME = "APPLICATION_NAME";

    String TRACE_ID = "TRACE_ID";

    String SPAN_ID = "SPAN_ID";

    String HOST_IP = "HOST_IP";

    String HOST_NAME = "HOST_NAME";

    String UNKNOWN = "unknown";

    String MDC_KEY = "tl";

    String LOGGER_PATTERN = String.format(" [%s, %s, %s, %s, %s]",
            APPLICATION_NAME,
            TRACE_ID,
            SPAN_ID,
            HOST_IP,
            HOST_NAME
    );

    int INITIAL_VALUE = 0;
}
