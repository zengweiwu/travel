package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid查询count收藏的次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 收藏表添加一条记录
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);
}
