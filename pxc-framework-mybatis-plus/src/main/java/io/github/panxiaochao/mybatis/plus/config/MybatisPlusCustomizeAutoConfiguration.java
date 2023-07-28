package io.github.panxiaochao.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.panxiaochao.core.utils.LocalhostUtil;
import io.github.panxiaochao.mybatis.plus.handler.MetaObjectCustomizeHandler;
import io.github.panxiaochao.mybatis.plus.properties.MpProperties;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@EnableConfigurationProperties(MpProperties.class)
public class MybatisPlusCustomizeAutoConfiguration {

	private final MpProperties mpProperties;

	/**
	 * 配置 mybatis plus 插件
	 * @return MybatisPlusInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 分页插件
		interceptor.addInnerInterceptor(paginationInnerInterceptor());
		// 乐观锁插件
		// interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
		return interceptor;
	}

	/**
	 * 分页插件
	 */
	public PaginationInnerInterceptor paginationInnerInterceptor() {
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		// 设置数据库类型
		paginationInnerInterceptor.setDbType(mpProperties.getDbType());
		paginationInnerInterceptor.setOptimizeJoin(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		paginationInnerInterceptor.setMaxLimit(-1L);
		// 分页合理化
		paginationInnerInterceptor.setOverflow(true);
		return paginationInnerInterceptor;
	}

	/**
	 * 乐观锁插件
	 */
	public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}

	/**
	 * Mybatis Plus 自动填充配置
	 * @return MetaObjectHandler
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MetaObjectCustomizeHandler();
	}

	/**
	 * 使用网卡信息绑定雪花生成器, 防止集群雪花ID重复
	 */
	@Bean
	public IdentifierGenerator idGenerator() {
		long workerId = LocalhostUtil.ipv4ToLong(LocalhostUtil.getLocalhostStr()) & 31;
		long dataCenterId = workerId > 30 ? 0 : workerId + 1;
		return new DefaultIdentifierGenerator(workerId, dataCenterId);
	}

}
