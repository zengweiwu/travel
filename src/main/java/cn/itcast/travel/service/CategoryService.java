package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * 分类信息服务接口
 */
public interface CategoryService {
    /**
     * 查找所有分类信心
     * @return
     */
    List<Category> findAll();
}
