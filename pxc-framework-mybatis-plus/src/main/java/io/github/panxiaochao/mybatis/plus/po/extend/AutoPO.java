package io.github.panxiaochao.mybatis.plus.po.extend;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.panxiaochao.mybatis.plus.po.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 主键采用数据库自增
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-17
 */
@Getter
@Setter
public class AutoPO extends BasePo {

	private static final long serialVersionUID = -2626183000481421947L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@TableId(value = "ID", type = IdType.AUTO)
	private Integer id;

}
