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
package io.github.panxiaochao.sensitive.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.panxiaochao.sensitive.enums.FSensitiveStrategy;
import io.github.panxiaochao.sensitive.serializer.jackson.FSensitiveJackJsonSerializer;
import io.github.panxiaochao.sensitive.strategy.AbstractFSensitiveStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 脱敏注解
 * </p>
 *
 * @author Lypxc
 * @since 2023-08-31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = FSensitiveJackJsonSerializer.class)
public @interface FSensitive {

	/**
	 * 处理策略
	 */
	FSensitiveStrategy strategy() default FSensitiveStrategy.DEFAULT;

	/**
	 * 自定义处理结果
	 */
	Class<? extends AbstractFSensitiveStrategy> customStrategy() default AbstractFSensitiveStrategy.class;

}
