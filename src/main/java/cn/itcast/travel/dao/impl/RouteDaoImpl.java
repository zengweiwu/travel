package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        //定义初始sql
        String sql = "select count(*) from tab_route where 1=1 ";
        //存储到字符缓冲区中
        StringBuilder sb = new StringBuilder(sql);
        //定义list来存储参数
        List params = new ArrayList();
        if (cid != 0){//如果cid不为空则作为条件拼入sql
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        //如果rname不为空
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        //执行sql 返回结果
        return jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray());
    }

    /**
     * 查询每页的记录数
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //定义初始sql
        String sql = "select * from tab_route where 1=1 ";
        //存储到字符缓冲区中
        StringBuilder sb = new StringBuilder(sql);
        //定义list来存储参数
        List params = new ArrayList();
        if (cid != 0){//如果cid不为空则作为条件拼入sql
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        //如果rname不为空
        if (rname != null || rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        //添加分页查询条件
        sb.append(" limit ?,?");
        sql = sb.toString();
        //加入分页查询参数
        params.add(start);
        params.add(pageSize);
        List<Route> routeList = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return routeList;
    }

    /**
     * 根据rid查询出一条路线的信心
     * @param rid
     * @return
     */
    @Override
    public Route findOneByRid(int rid) {
        //定义sql
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
