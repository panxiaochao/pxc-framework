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
package io.github.panxiaochao.trace.log.core.mdc;

import io.github.panxiaochao.trace.log.constant.LogConstants;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@code MdcHelper}
 * <p> description: MDC 辅助类
 *
 * @author Lypxc
 * @since 2023-01-17
 */
public class MDCHelper {

    public static void putContextMap(Map<String, String> contextMap) {
        for (Map.Entry<String, String> kvEntry : contextMap.entrySet()) {
            MDC.put(kvEntry.getKey(), kvEntry.getValue());
        }
    }

    public static Map<String, String> getMdcCopyContextMap() {
        Map<String, String> ccm = MDC.getCopyOfContextMap();
        if (Objects.isNull(ccm)) {
            return new HashMap<>();
        }
        return ccm;
    }

    /**
     * 清除所有记录
     */
    public static void removeAll() {
        MDC.remove(LogConstants.MDC_KEY);
        MDC.remove(LogConstants.TRACE_ID);
        MDC.remove(LogConstants.SPAN_ID);
        MDC.remove(LogConstants.HOST_IP);
        MDC.remove(LogConstants.HOST_NAME);
        MDC.remove(LogConstants.APPLICATION_NAME);
    }
}
