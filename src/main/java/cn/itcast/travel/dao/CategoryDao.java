package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * 分类展示接口
 */
public interface CategoryDao {
    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAll();
}
