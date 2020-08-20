package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    RouteService routeService = new RouteServiceImpl();

    /**
     * 旅游路线分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String currentPageStr = request.getParameter("currentPage");//当前页码
        String pageSizeStr = request.getParameter("pageSize");//每页记录条数
        String cidStr = request.getParameter("cid");//查询的类别
        String rname = request.getParameter("rname");//查询条件

        //System.out.println(rname);
        //处理参数
        int cid = 0;
        int currentPage = 0;
        int pageSize = 0;
        //如果客户端提交的参数是空的 在服务器获取出来的不是空字符串而是字符串“null”
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //
        if (currentPageStr != null && currentPageStr.length() > 0 ){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;//默认值设为1
        }
        //|| pageSizeStr.length() > 0 这里报空指针异常的话不能执行反射
        if (pageSizeStr != null && pageSizeStr.length() > 0 ){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;//默认每页显示5条数据
        }
        //调用service findBypage（）完成查询
        PageBean<Route> pageBean = routeService.findByPage(cid, currentPage, pageSize,rname);
        //System.out.println(pageBean);
        //响应结果
        writeValue(response,pageBean);
    }

    /**
     * 路线详情查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String ridStr = request.getParameter("rid");
        //处理参数
        int rid = 0;
        if (ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)){
            rid = Integer.parseInt(ridStr);
        }
        //调用service
        Route route = routeService.findOne(rid);
        //将对象序列化为json写回客户端
        writeValue(response,route);
    }

    /**
     *判断用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数:线路id
        String rid = request.getParameter("rid");
        //获取当前登录的用户id
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            //用户未登录
            uid = 0;
        }else {
            //用户已登录
            uid = user.getUid();
        }
        //调用FavoriteService查询是否收藏
        FavoriteService favoriteService = new FavoriteServiceImpl();
        boolean favoriteFlag = favoriteService.isFavorite(rid, uid);
        //回写结果
        writeValue(response,favoriteFlag);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数:线路rid
        String rid = request.getParameter("rid");
        //获取当前登录的用户id
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            //用户未登录 直接返回
            return;
        }else {
            //用户已登录
            uid = user.getUid();
        }
        //调用service
        FavoriteService favoriteService = new FavoriteServiceImpl();
        favoriteService.add(rid,uid);
    }
}
