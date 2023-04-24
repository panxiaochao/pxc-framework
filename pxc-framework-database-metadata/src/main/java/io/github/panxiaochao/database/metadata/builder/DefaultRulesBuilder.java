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
package io.github.panxiaochao.database.metadata.builder;

import lombok.Getter;

/**
 * {@code DefaultRulesBuilder}
 * <p> description: 默认规则构造
 *
 * @author Lypxc
 * @since 2023-04-20
 */
public class DefaultRulesBuilder {

    private DefaultRulesBuilder() {
    }

    /**
     * 是否跳过视图, 默认 false
     */
    @Getter
    private boolean skipView;

    /**
     * <p>查询表的时候是否填充字段，默认 false
     * <p>主要是考虑在数量很多表的情况下，有性能问题
     */
    @Getter
    private boolean fillColumns;

    public static class Builder {

        private final DefaultRulesBuilder rulesBuilder;

        public Builder() {
            this.rulesBuilder = new DefaultRulesBuilder();
        }

        /**
         * 开启跳过视图
         *
         * @return this
         */
        public Builder enableSkipView() {
            this.rulesBuilder.skipView = true;
            return this;
        }

        /**
         * 开启填充数据库表字段
         *
         * @return this
         */
        public Builder enableFillColumns() {
            this.rulesBuilder.fillColumns = true;
            return this;
        }

        /**
         * 构建规则配置
         */
        public DefaultRulesBuilder build() {
            return this.rulesBuilder;
        }
    }
}
