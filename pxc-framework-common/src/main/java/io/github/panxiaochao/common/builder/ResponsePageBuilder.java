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
package io.github.panxiaochao.common.builder;


import io.github.panxiaochao.common.builder.page.RequestPage;
import io.github.panxiaochao.common.builder.page.ResponsePage;

import java.util.Collections;
import java.util.List;

/**
 * <p>This is ResponsePageBuilder java for ResponsePage
 *
 * @author Lypxc
 * @since 2021/12/3 17:47
 */
@Deprecated
public class ResponsePageBuilder {
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
     * 分页记录
     */
    private List<?> rowRecords = Collections.emptyList();

    /**
     * 静态创建
     *
     * @return ResponsePageBuilder
     */
    public static ResponsePageBuilder create() {
        return new ResponsePageBuilder();
    }

    /**
     * Updates the response pageNo
     *
     * @param pageNo 页码
     * @return ResponsePageBuilder
     */
    public ResponsePageBuilder pageNo(long pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    /**
     * Updates the response pageSize
     *
     * @param pageSize 分页
     * @return ResponsePageBuilder
     */
    public ResponsePageBuilder pageSize(long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Updates the response totalCount
     *
     * @param totalCount 总条数
     * @return ResponsePageBuilder
     */
    public ResponsePageBuilder totalCount(long totalCount) {
        this.totalCount = totalCount;
        // 页数根据传入的总行数以及每页显示的行数，求出总页数
        this.totalPages = (this.totalCount + this.pageSize - 1) / this.pageSize;
        return this;
    }

    /**
     * Updates the response rowRecords
     *
     * @param rowRecords 记录
     * @return ResponsePageBuilder
     */
    public ResponsePageBuilder rowRecords(List<?> rowRecords) {
        this.rowRecords = rowRecords;
        return this;
    }

    /**
     * Updates the response, simpleSetup the params
     *
     * @param requestPage 请求分页
     * @param totalCount  总条数
     * @param rowRecords  记录
     * @return ResponsePageBuilder
     */
    public ResponsePageBuilder simpleSetup(RequestPage requestPage, long totalCount, List<?> rowRecords) {
        this.pageNo = requestPage.getPageNo();
        this.pageSize = requestPage.getPageSize();
        this.totalCount = totalCount;
        // 方法一：页数根据传入的总行数以及每页显示的行数，求出总页数
        // this.totalPages = (this.totalCount + this.pageSize - 1) / this.pageSize;
        // 方法二
        this.totalPages = getTotalPages(totalCount, pageSize);
        this.rowRecords = rowRecords;
        return this;
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

    /**
     * @return ResponsePage
     */
    public ResponsePage build() {
        return new ResponsePage(pageNo, pageSize, totalCount, totalPages, rowRecords);
    }
}
