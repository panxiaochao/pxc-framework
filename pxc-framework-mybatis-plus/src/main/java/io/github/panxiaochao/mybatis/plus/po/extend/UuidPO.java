package io.github.panxiaochao.mybatis.plus.po.extend;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.panxiaochao.mybatis.plus.po.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 主键采用UUID
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-17
 */
public class UuidPO extends BasePo {

	private static final long serialVersionUID = 230788523080038096L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@TableId(value = "ID", type = IdType.ASSIGN_UUID)
	private String id;

}
