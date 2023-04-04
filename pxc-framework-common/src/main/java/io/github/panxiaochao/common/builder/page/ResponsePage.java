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
package io.github.panxiaochao.common.builder.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <p> 响应结果类
 *
 * @author Lypxc
 * @since 2021/12/3 17:51
 */
@Getter
@Setter
@Deprecated
public class ResponsePage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页号
     */
    private long pageNo = 1;

    /**
     * 页数
     */
    private long pageSize = 10;

    /**
     * 总数
     */
    private long totalCount = 0;

    /**
     * 总页码数
     */
    private long totalPages = 0;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 分页记录
     */
    private List<?> rowRecords;

    public ResponsePage(long pageNo, long pageSize, long totalCount, long totalPages, List<?> rowRecords) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.rowRecords = rowRecords == null ? Collections.emptyList() : rowRecords;
        // 判断是否有上一页和下一页
        this.hasPrevious = (getPageNo() > 1 && getPageNo() <= this.getTotalPages());
        this.hasNext = getPageNo() < getTotalPages();
    }
}
