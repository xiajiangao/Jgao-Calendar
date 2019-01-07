package top.jgao.persistence.request;

import org.springframework.util.Assert;

/**
 * 通用分页接受类
 *
 * @author jiangao.xia.o
 */
public class PageParams {

    private Integer limit;
    private Integer offset;
    private Integer pageNum;
    private Integer pageSize;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if (pageSize == null) {
            this.pageSize = limit;
        }
        if (pageNum == null && offset != null) {
            this.pageNum = (offset / limit) + 1;
        }
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        if (pageNum == null && limit != null) {
            this.pageNum = (offset / limit) + 1;
        }
        this.offset = offset;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if (offset == null && pageSize != null) {
            this.offset = (pageNum - 1) * pageSize;
        }
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (limit == null) {
            this.limit = pageSize;
        }
        if (offset == null && pageNum != null) {
            this.offset = (pageNum - 1) * pageSize;
        }
        this.pageSize = pageSize;
    }

    public boolean notEmpty() {
        return pageNum != null && pageSize != null;
    }

    public void checkNotNull() {
        Assert.notNull(pageNum, "分页参数不能为空");
        Assert.notNull(pageSize, "分页参数不能为空");
    }
}
