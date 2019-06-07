package com.xuhe.platform.entity.query;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
public class PageQuery {
    protected Integer pageNum = 1;
    protected Integer pageSize = 10;

    public PageQuery() {
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
}
