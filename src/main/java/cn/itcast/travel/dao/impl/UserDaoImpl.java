package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where username = ?";
            //执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
            //出异常直接返回null
        }
        return user;
    }

    /**
     * 保存用户信息
     *
     * @param user
     */
    @Override
    public void saveUser(User user) {
        //定义sql
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //执行sql
        jdbcTemplate.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    /**
     * 根据code查询用户
     * @return
     */
    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where code = ?";
            //执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        //定义sql
        String sql = "update tab_user set status = 'Y' where code = ?";
        //执行sql
        jdbcTemplate.update(sql,user.getCode());
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        }catch (Exception e){
            //出异常直接返回null
        }
        return user;
    }
}
