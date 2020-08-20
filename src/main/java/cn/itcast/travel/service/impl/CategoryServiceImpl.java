package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    /**
     * 查找所有分类信息
     * @return
     */
    @Override
    public List<Category> findAll() {
        //先从redis中查 如果缓存中没有则从数据库中查
        //通过jedis获取redis客户端
        Jedis jedis = JedisUtil.getJedis();
        //查询缓存
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        //定义List集合来存储查询出来的数据
        List<Category> cs = null;
        if (category == null || category.size() == 0){
            System.out.println("从数据库中查询了...");
            //缓存中没有 则查询数据库
            cs = categoryDao.findAll();
            //将查询出来的数据存入缓存
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());//以cid为分数按照分数来排序
            }
            return cs;
        }else {
            //要先启动redis服务器
            System.out.println("从缓存中查询了...");
            cs = new ArrayList<Category>();
            //如果缓存中有数据 则将从缓存中查出来的数据转换为list集合返回
            for (Tuple tuple : category) {
                Category c = new Category();//创建category对象 设置好cname后存入cs集合
                c.setCid((int)tuple.getScore());
                c.setCname(tuple.getElement());
                cs.add(c);
            }
            return cs;
        }
    }
}
