package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    RouteDao routeDao = new RouteDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();
    RouteImgDao routeImgDao = new RouteImgDaoImpl();
    FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 查询返回分页对象
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname) {
        //设置currentPage 和 pageSize
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //查询总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //查询该页显示的数据
        int start = (currentPage - 1) * pageSize;//计算开始查询的页码
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pageBean.setList(list);
        //System.out.println(list);
        //计算总页数
        int pageTotal = totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize)+1;
        pageBean.setPageTotal(pageTotal);
        return pageBean;
    }

    /**
     * 根据rid查询出一条详细路线信息
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        //得到一个route对象
        Route route = routeDao.findOneByRid(rid);
        //根据查询出来的route对象获取sid查询seller
        Seller seller = sellerDao.findBySid(route.getSid());
        //根据rid查询一条路线对应的所有图片
        List<RouteImg> routeImgs = routeImgDao.findByRid(rid);
        //封装route
        route.setSeller(seller);
        route.setRouteImgList(routeImgs);

        //查询收藏的次数
        int fav_count = favoriteDao.findCountByRid(rid);
        route.setCount(fav_count);
        return route;
    }
}
