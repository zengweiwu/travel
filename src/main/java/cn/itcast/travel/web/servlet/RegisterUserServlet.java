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

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//使用一次后失效
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //储存错误信息
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            ObjectMapper mapper = new ObjectMapper();
            String info_json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(info_json);
            //如果验证码不正确直接返回 不再进行用户名判断
            return;
        }
        //1 获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        //2 封装对象
        User register_user = new User();
        try {
            BeanUtils.populate(register_user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //3 调用UserService服务完成注册
        UserService userService = new UserServiceImpl();
        boolean flag = userService.register(register_user);
        //4 响应结果
        ResultInfo info = new ResultInfo();
        if (flag){
            //注册成功 设置响应对象的状态为true
            info.setFlag(true);
        }else {
            //注册失败 设置响应对象的状态为false
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        //info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String info_json = mapper.writeValueAsString(info);
        //回写json
        //设置
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(info_json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
