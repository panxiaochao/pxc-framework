package io.github.panxiaochao.common.plugin.datasource.builder;

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

    public static class Builder {

        private final DefaultRulesBuilder rulesBuilder;

        public Builder() {
            this.rulesBuilder = new DefaultRulesBuilder();
        }

        /**
         * 开启跳过视图
         *
         * @return this
         * @since 3.5.0
         */
        public Builder enableSkipView() {
            this.rulesBuilder.skipView = true;
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
