package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 抽取基础servlet：
 *      通过此servlet完成方法的调用
 */

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();
    //在此方法中完成方法的选择调用
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //获取请求的uri
        String uri = req.getRequestURI();//  user/add
        //System.out.println(uri);
        //获取方法名称
        String mothdeone = uri.substring(uri.lastIndexOf('/') + 1);
        //System.out.println(mothdeone);// add
        //通过类对象获取对应方法对象 这里的this是指的继承此类的类对象
        try {
            Method method = this.getClass().getMethod(mothdeone, HttpServletRequest.class, HttpServletResponse.class);
            //反射执行该方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    //序列化并回写writeValue方法
    public void writeValue(HttpServletResponse response,Object obj) throws IOException {
        //设置响应内容
        response.setContentType("application/json;charset=utf-8");
        //序列化
        mapper.writeValue(response.getWriter(),obj);
    }
    //对象序列化方法 返回json字符串
    public String writeValueAsString(Object obj){
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
