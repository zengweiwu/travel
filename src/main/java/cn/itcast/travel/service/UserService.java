package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 用户注册
     * @param register_user
     * @return
     */
    boolean register(User register_user);

    /**
     * 用户激活
     * @return
     * @param code
     */
    boolean activeUser(String code);

    /**
     * 用户登录
     * @return
     * @param login_user
     */
    User loginUser(User login_user);
}
