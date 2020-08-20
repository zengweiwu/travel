package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    /**
     * 用户注册
     *
     * @param register_user
     * @return
     */
    @Override
    public boolean register(User register_user) {
        //1 根据用户名查询用户信息
        User user = userDao.findUserByUsername(register_user.getUsername());
        //2 保存用户信息
        if (user != null) {
            //如果查询到user则注册失败 不保存信息
            return false;
        }
        //注册成功 保存user
        //生成设置激活码
        String code = UuidUtil.getUuid();
        register_user.setCode(code);
        //设置激活转态为未激活N
        register_user.setStatus("N");
        //保存用户信息
        userDao.saveUser(register_user);
        //发送激活邮件
        //获取邮箱号
        String registerUserEmail = register_user.getEmail();
        //邮件内容
        String context = "<a href='http://localhost/travel/user/activeUser?code="+register_user.getCode()+"'>点我激活【黑马旅游网】</a>";
        MailUtils.sendMail(registerUserEmail,context,"激活邮件");
        return true;

    }

    /**
     * 用户激活
     * @return
     * @param code
     */
    @Override
    public boolean activeUser(String code) {
        //根据code查询用户
        User user = userDao.findUserByCode(code);
        if (user != null){
            //如果查询到用户则修改status为Y
            userDao.updateStatus(user);
            return true;
        }
        return false;
    }

    /**
     * 用户登录
     * @return
     * @param login_user
     */
    @Override
    public User loginUser(User login_user) {
        User user = userDao.findUserByUsernameAndPassword(login_user.getUsername(),login_user.getPassword());
        return user;
    }
}
