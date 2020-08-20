package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 获取用户名和密码
        Map<String, String[]> map = request.getParameterMap();
        //2 封装对象
        User login_user = new User();
        try {
            BeanUtils.populate(login_user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //3 调用UserService
        UserService userService = new UserServiceImpl();
        User user = userService.loginUser(login_user);
        //将返回的结果存入session 用于显示欢迎信息
        HttpSession login_session = request.getSession();
        login_session.setAttribute("user",user);
        //4 响应结果
        ResultInfo info = new ResultInfo();
        if (user == null){
            //如果没有查询到user
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误登录失败！");
        }
        if (user != null){
            if (user.getStatus().equals("N")){
                //如果用户未激活
                info.setFlag(false);
                info.setErrorMsg("用户未激活，请先激活");
            }
            if (user.getStatus().equals("Y")){
                //如果用户已激活 设置标记为true 由前端进行页面跳转
                info.setFlag(true);
            }
        }
        //序列化info 回写数据
        ObjectMapper mapper = new ObjectMapper();
        //设置响应内容
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
