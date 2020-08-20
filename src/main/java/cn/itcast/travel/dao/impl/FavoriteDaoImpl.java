package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try{
            //定义sql
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            //执行sql 返回查询对象
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return favorite;
    }


    /**
     * 根据rid查询count收藏次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        //定义sql
        String sql = "select count(*) from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 收藏表添加一条记录
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        //定义sql
        String sql = "insert into tab_favorite values (?,?,?)";
        //执行sql
        jdbcTemplate.update(sql,rid,new Date(),uid);
    }
}
