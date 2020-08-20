package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 判断当前用户是否收藏该路线
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(String rid, int uid);
}
