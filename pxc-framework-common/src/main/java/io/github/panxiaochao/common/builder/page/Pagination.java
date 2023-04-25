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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code Pagination}
 * <p> description: 分页对象
 *
 * @author Lypxc
 * @since 2023-01-03
 */
@Getter
@Setter
@Schema(description = "分页对象")
public class Pagination {

    /**
     * 页码
     */
    @Schema(description = "页码，不小于1")
    private long pageNo = 1;

    /**
     * 页数
     */
    @Schema(description = "页数")
    private long pageSize = 10;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private long total = 0;

    /**
     * 总页码数
     */
    @Schema(description = "总页码数")
    private long totalPages = 0;

    public Pagination() {
    }


    public Pagination(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPages = getTotalPages(total, pageSize);
    }

    public Pagination(Long pageNo, Long pageSize, Long total) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = getTotalPages(total, pageSize);
    }

    /**
     * 是否有上一页
     */
    public boolean getHasPrevious() {
        return (getPageNo() > 1 && getPageNo() <= this.getTotalPages());
    }

    /**
     * 是否有下一页
     */
    public boolean getHasNext() {
        return getPageNo() < getTotalPages();
    }

    /**
     * @param totalCount 总条数
     * @param pageSize   分页
     * @return ResponsePageBuilder
     */
    private long getTotalPages(long totalCount, long pageSize) {
        if (totalCount == 0) {
            return 0L;
        }
        long pages = totalCount / pageSize;
        if (totalCount % pageSize != 0) {
            pages++;
        }
        return pages;
    }
}
