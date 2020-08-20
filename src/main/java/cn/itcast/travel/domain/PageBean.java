package cn.itcast.travel.domain;

import java.util.List;

/**
 * 分页实体
 */
public class PageBean<T> {
    private int pageTotal;//总页数
    private int totalCount;//总记录数
    private int currentPage;//当前页码
    private int pageSize;//一页显示的记录数
    private List<T> list;//路线集合

    @Override
    public String toString() {
        return "PageBean{" +
                "pageTotal=" + pageTotal +
                ", totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
