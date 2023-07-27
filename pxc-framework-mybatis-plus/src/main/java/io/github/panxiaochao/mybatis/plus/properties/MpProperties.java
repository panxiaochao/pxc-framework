package io.github.panxiaochao.mybatis.plus.properties;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 自定义属性配置文件
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-17
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "mybatis-plus", ignoreInvalidFields = true)
public class MpProperties {

	/**
	 * 数据库类型, 默认Mysql类型
	 */
	private DbType dbType = DbType.MYSQL;

}
