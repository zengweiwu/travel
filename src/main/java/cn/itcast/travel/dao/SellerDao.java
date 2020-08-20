package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * 商家数据库操作类
 */
public interface SellerDao {
    /**
     * 根据sid查询出路线对应的商家信息
     * @param sid
     * @return
     */
    public Seller findBySid(int sid);
}
