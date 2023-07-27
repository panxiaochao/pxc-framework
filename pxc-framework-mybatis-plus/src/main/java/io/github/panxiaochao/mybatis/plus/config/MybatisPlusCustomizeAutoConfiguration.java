package io.github.panxiaochao.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.panxiaochao.mybatis.plus.handler.MetaObjectCustomizeHandler;
import io.github.panxiaochao.mybatis.plus.properties.MpProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * MyBatis plus 自动配置类
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-17
 */
@AutoConfiguration
@EnableConfigurationProperties(MpProperties.class)
public class MybatisPlusCustomizeAutoConfiguration {

	/**
	 * 配置 mybatis plus 插件
	 * @return MybatisPlusInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(final MpProperties mpProperties) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 分页配置
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(
				mpProperties.getDbType());
		paginationInnerInterceptor.setOptimizeJoin(false);
		interceptor.addInnerInterceptor(paginationInnerInterceptor);
		return interceptor;
	}

	/**
	 * Mybatis Plus 自动填充配置
	 * @return MetaObjectHandler
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MetaObjectCustomizeHandler();
	}

}
