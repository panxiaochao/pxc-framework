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
package io.github.panxiaochao.minio.constants;

/**
 * {@code MinioConstant}
 * <p> description: MinioConstant 常量类
 *
 * @author Lypxc
 * @since 2023-05-11
 */
public interface MinioConstant {

    // 分块大小
    int DEFAULT_CHUNK_SIZE = 10 * 1024 * 1024;

    // 预签名url过期时间(ms)
    Long PRE_SIGN_URL_EXPIRE = 60 * 10 * 1000L;
}
