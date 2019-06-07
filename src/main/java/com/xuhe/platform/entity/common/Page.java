package com.xuhe.platform.entity.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
public class Page<T> implements Serializable {
    protected Integer pageNum = 1;
    protected Integer pageSize = -1;
    protected Integer pages = 1;
    protected List<T> list = new ArrayList();
    protected Integer totalCount = -1;

    public Page() {
    }

    public Page(int pageNum, int pageSize, int pages, long totalCount, List<T> data) {
        this.pageNum = pageNum;
        if (pageNum < 1) {
            this.pageNum = 1;
        }

        this.pageSize = new Integer(pageSize);
        this.pages = new Integer(pages);
        this.totalCount = new Integer((int)totalCount);
        this.list.clear();
        this.list.addAll(data);
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return this.pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
