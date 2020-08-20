package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 通过cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    public int findTotalCount(int cid, String rname);

    /**
     * 根据cid star pageSize查询一页的数据
     * @param cid
     * @param start
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 根据rid查询出一条路线信息
     * @param rid
     * @return
     */
    public  Route findOneByRid(int rid);
}
