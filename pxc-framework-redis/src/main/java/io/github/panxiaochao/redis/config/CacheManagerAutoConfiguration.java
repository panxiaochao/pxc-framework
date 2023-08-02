package io.github.panxiaochao.redis.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.panxiaochao.redis.constants.CacheManagerType;
import io.github.panxiaochao.redis.manager.CustomizerCaffeineCacheManager;
import io.github.panxiaochao.redis.manager.CustomizerRedissonSpringCacheManager;
import io.github.panxiaochao.redis.properties.Redisson3Properties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 缓存自动配置类
 * </p>
 *
 * @author Lypxc
 * @since 2023-08-01
 */
@AutoConfiguration
@EnableConfigurationProperties({ Redisson3Properties.class })
public class CacheManagerAutoConfiguration {

	/**
	 * 自定义 CacheManager 缓存管理器
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(final Redisson3Properties redisson3Properties) {
		if (CacheManagerType.redis.equals(redisson3Properties.getCacheType())) {
			// 使用自定义 RedissonSpringCacheManager 缓存管理器
			return new CustomizerRedissonSpringCacheManager();
		}
		else if (CacheManagerType.caffeine.equals(redisson3Properties.getCacheType())) {
			// 使用自定义 CaffeineCacheManager 缓存管理器
			CustomizerCaffeineCacheManager caffeineCacheManager = new CustomizerCaffeineCacheManager();
			caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
				// 设置过期时间
				.expireAfterWrite(1, TimeUnit.MINUTES)
				// 初始化缓存空间大小
				.initialCapacity(100)
				// 最大的缓存条数
				.maximumSize(200));
			return caffeineCacheManager;
		}
		return new ConcurrentMapCacheManager();
	}

}
