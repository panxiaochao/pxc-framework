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
package io.github.panxiaochao.sensitive.serializer.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import io.github.panxiaochao.sensitive.annotation.FSensitive;
import io.github.panxiaochao.sensitive.enums.FSensitiveStrategy;
import io.github.panxiaochao.sensitive.strategy.AbstractFSensitiveStrategy;
import io.github.panxiaochao.sensitive.utils.InvokeMethodSensitiveUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 脱敏JSON序列化
 * </p>
 *
 * @author Lypxc
 * @since 2023-08-31
 */
public class FSensitiveJackJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

	private FSensitiveStrategy strategy;

	/**
	 * 自定义策略ClassName
	 */
	private String customStrategyClassName;

	private FSensitiveJackJsonSerializer() {
	}

	private FSensitiveJackJsonSerializer(FSensitiveStrategy strategy, String customStrategyClassName) {
		this.strategy = strategy;
		this.customStrategyClassName = customStrategyClassName;
	}

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		try {
			// 相同的class，使用自带策略
			if (customStrategyClassName.equals(AbstractFSensitiveStrategy.class.getName())) {
				gen.writeString(strategy.desensitize().apply(value));
			}
			else {
				String sensitiveValue = InvokeMethodSensitiveUtil.invokeSensitiveMethod(customStrategyClassName, value);
				gen.writeString(sensitiveValue);
			}
		}
		catch (IOException e) {
			gen.writeString(value);
		}
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		FSensitive annotation = property.getAnnotation(FSensitive.class);
		if (null == annotation) {
			annotation = property.getContextAnnotation(FSensitive.class);
		}
		if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
			if (null == annotation.strategy()) {
				throw new RuntimeException("The annotation `@FSensitive` attribute strategy is empty!");
			}
			return new FSensitiveJackJsonSerializer(annotation.strategy(), annotation.customStrategy().getName());
		}
		return prov.findValueSerializer(property.getType(), property);
	}

}
