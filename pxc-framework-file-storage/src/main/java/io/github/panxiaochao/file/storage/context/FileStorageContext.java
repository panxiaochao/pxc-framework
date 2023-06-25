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
package io.github.panxiaochao.file.storage.context;

import io.github.panxiaochao.common.filemeta.FileMetadata;
import io.github.panxiaochao.file.storage.core.FileStorageType;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * {@code FileStorageContext}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public interface FileStorageContext extends Context {

    /**
     * Returns the {@link FileStorageType}.
     *
     * @return the {@link FileStorageType}
     */
    default FileStorageType getStorageType() {
        return get(FileStorageType.class);
    }

    /**
     * Returns the {@link FileStorageProperties}.
     *
     * @return Returns the {@link FileStorageProperties}.
     */
    default FileStorageProperties getFileStorageProperties() {
        return get(FileStorageProperties.class);
    }

    /**
     * Returns the {@link FileMetadata} fileMetadata.
     *
     * @param <T> the type of the {@code FileMetadata}
     * @return the {@link FileMetadata} fileMetadata
     */
    default <T extends FileMetadata> T getFileMetadata() {
        return get(AbstractBuilder.FILE_METADATA_KEY);
    }


    abstract class AbstractBuilder<T extends FileStorageContext, B extends AbstractBuilder<T, B>> {

        private static final String FILE_METADATA_KEY =
                FileMetadata.class.getName().concat(".FILE_METADATA");

        private final Map<Object, Object> context = new HashMap<>();

        /**
         * Sets the {@link FileStorageType storageType}.
         *
         * @param storageType the {@link FileStorageType}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B storageType(FileStorageType storageType) {
            return put(FileStorageType.class, storageType);
        }

        /**
         * Sets the {@link FileStorageProperties}.
         *
         * @param fileStorageProperties the {@link FileStorageProperties}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B fileStorageProperties(FileStorageProperties fileStorageProperties) {
            return put(FileStorageProperties.class, fileStorageProperties);
        }

        /**
         * Sets the {@link FileMetadata fileMetadata}.
         *
         * @param fileMetadata the {@link FileMetadata}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B fileMetadata(FileMetadata fileMetadata) {
            return put(FILE_METADATA_KEY, fileMetadata);
        }

        /**
         * Associates an attribute.
         *
         * @param key   the key for the attribute
         * @param value the value of the attribute
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B put(Object key, Object value) {
            Assert.notNull(key, "key cannot be null");
            Assert.notNull(value, "value cannot be null");
            this.context.put(key, value);
            return getThis();
        }

        /**
         * A {@code Consumer} of the attributes {@code Map}
         * allowing the ability to add, replace, or remove.
         *
         * @param contextConsumer a {@link Consumer} of the attributes {@code Map}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B context(Consumer<Map<Object, Object>> contextConsumer) {
            contextConsumer.accept(this.context);
            return getThis();
        }

        @SuppressWarnings("unchecked")
        protected <V> V get(Object key) {
            return (V) this.context.get(key);
        }

        protected Map<Object, Object> getContext() {
            return this.context;
        }

        @SuppressWarnings("unchecked")
        protected final B getThis() {
            return (B) this;
        }

        /**
         * Builds a new {@link FileStorageContext}.
         *
         * @return the {@link FileStorageContext}
         */
        public abstract T build();
    }
}
