package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * User数据库操作接口
 */
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 保存用户信息
     * @param user
     */
    public  void saveUser(User user);

    /**
     * 根据激活码查询用用
     * @return
     */
    User findUserByCode(String code);

    /**
     * 修改用户的status
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username, String password);
}
