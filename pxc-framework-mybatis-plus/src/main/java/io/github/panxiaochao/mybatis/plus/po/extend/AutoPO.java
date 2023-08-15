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
