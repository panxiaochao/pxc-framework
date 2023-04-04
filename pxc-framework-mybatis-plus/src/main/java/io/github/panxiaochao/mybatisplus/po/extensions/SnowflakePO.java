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
package io.github.panxiaochao.mybatisplus.po.extensions;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.panxiaochao.mybatisplus.po.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code SnowflakePO}
 * <p> 基础持久化实体
 * <p> 主键采用雪花算法生成ID
 *
 * @author Lypxc
 * @since 2022/10/25
 */
@Getter
@Setter
@ToString
public class SnowflakePO extends BasePo {

    /**
     * 主键
     */
    @Schema(description = "主键")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;
}
