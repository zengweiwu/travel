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

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();
    /**
     * 注册方法
     * @param request
     * @param response
     */
    public void register(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
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
        //UserService userService = new UserServiceImpl();
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
        //ObjectMapper mapper = new ObjectMapper();
        //String info_json = mapper.writeValueAsString(info);
        //回写json
        //设置
        //response.setContentType("application/json;charset=utf-8");
        //response.getWriter().write(info_json);
        writeValue(response,info);
    }

    /**
     * 登录方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
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
        //UserService userService = new UserServiceImpl();
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
        //ObjectMapper mapper = new ObjectMapper();
        //设置响应内容
        //response.setContentType("application/json;charset=utf-8");
        //mapper.writeValue(response.getWriter(),info);
        writeValue(response,info);
    }

    /**
     * 查找当前用户方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        //从session中获取User对象
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //响应结果
        writeValue(response,user);
        //ObjectMapper mapper = new ObjectMapper();
        //设置响应内容
        //response.setContentType("application/json;charset=utf-8");
        //序列化
        //mapper.writeValue(response.getWriter(),user);
    }

    /**
     * 退出方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        //删除session
        request.getSession().invalidate();
        //重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void activeUser(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        //1 获取激活码
        String code = request.getParameter("code");
        if (code != null){
            //2 调用UserService方法activeUser 返回结果
            //UserService userService = new UserServiceImpl();
            boolean flag = userService.activeUser(code);
            //3 响应结果
            String msg = null;
            if (flag){
                //激活成功
                msg = "激活成功请<a href='http://localhost/travel/login.html'>登录</a>";
            }else {
                //激活失败
                msg = "激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
