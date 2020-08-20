package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     * 分页查询返回pagebean
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname);

    /**
     *根据rid查询出一条路线信息
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
