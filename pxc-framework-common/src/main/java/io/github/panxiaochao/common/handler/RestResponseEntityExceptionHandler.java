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
package io.github.panxiaochao.common.handler;

import io.github.panxiaochao.common.enums.CommonResponseEnum;
import io.github.panxiaochao.common.enums.ServletResponseEnum;
import io.github.panxiaochao.common.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * {@code RestResponseEntityExceptionHandler}
 * <p> description: 默认情况下，标准的Spring MVC异常会通过DefaultHandlerExceptionResolver来处理，自定继承处理
 *
 * @author Lypxc
 * @since 2023-03-06
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        LOGGER.error(e.getMessage(), e);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", e, 0);
        }
        ServletResponseEnum servletExceptionEnum = null;
        try {
            servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
        } catch (IllegalArgumentException e1) {
            LOGGER.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
            return new ResponseEntity<>(R.fail(CommonResponseEnum.INTERNAL_SERVER_ERROR.getCode(), CommonResponseEnum.INTERNAL_SERVER_ERROR.getMessage(), body), headers, status);
        }
        return new ResponseEntity<>(R.fail(servletExceptionEnum.getCode(), servletExceptionEnum.getMessage(), body), headers, status);
    }
}
