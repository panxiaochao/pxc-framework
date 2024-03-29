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
package io.github.panxiaochao.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 顶层Mapper
 * </p>
 *
 * @author Lypxc
 * @since 2023-09-08
 */
public interface RootMapper<T> extends BaseMapper<T> {

	/**
	 * 批量新增
	 * @param entityList 实体数组
	 * @return 成功数
	 */
	int insertBatchSomeColumn(List<T> entityList);

	/**
	 * 批量更新
	 * @param entityList 实体数组
	 * @return 成功数
	 */
	int updateBatchSomeColumn(List<T> entityList);

}
