package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * 旅游路线图片数据库操作类
 */
public interface RouteImgDao {
    /**
     * 通过rid查询所有路线对应的图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
