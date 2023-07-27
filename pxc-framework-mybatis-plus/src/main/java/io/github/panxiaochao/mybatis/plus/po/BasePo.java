package io.github.panxiaochao.mybatis.plus.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 基础持久化实体
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-17
 */
@Getter
@Setter
public abstract class BasePo implements Serializable {

	private static final long serialVersionUID = 846044951522308141L;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	// @Schema(description = "创建人")
	// @TableField(value = "CREATE_ID", fill = FieldFill.INSERT)
	// private Long createId;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	// @Schema(description = "更新人")
	// @TableField(value = "UPDATE_ID", fill = FieldFill.INSERT_UPDATE)
	// private Long updateId;

}
