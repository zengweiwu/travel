package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询所有分类信息方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用categoryService findAll完成查询
        List<Category> categorylist = categoryService.findAll();
        //回写结果
        writeValue(response,categorylist);
    }

}
