package com.yylnb.service.impl;

import com.yylnb.entity.Commodity;
import com.yylnb.entity.User;
import com.yylnb.mapper.CommodityMapper;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.CommodityService;
import com.yylnb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author RainLin
 * @date 2020/1/28 - 13:46
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 上传商品
     *
     * @param commodity
     */
    @Override
    public void insertCommodity(Commodity commodity) {
        commodityMapper.insertCommodity(commodity);
    }


    /**
     * 根据状态码返回商品集合
     *
     * @param state
     * @return
     */
    @Override
    public List<Commodity> findCommoditiesByState(Integer state) {
        List<Commodity> commodities = commodityMapper.findCommoditiesByState(state);
        //将轮播图地址处理为数组方便展示
        for (Commodity commodity : commodities) {
            commodity.setCarousel_array(commodity.getCarousel().split(","));
        }
        return commodities;
    }

    /**
     * 根据用户id和状态码 查找商品
     *
     * @param user_id
     * @param state
     * @return
     */
    @Override
    public List<Commodity> findCommoditiesByUserIdAndState(Integer user_id, Integer state) {
        List<Commodity> commodities = commodityMapper.findCommoditiesByUserIdAndState(user_id, state);
        //将轮播图地址处理为数组方便展示
        for (Commodity commodity : commodities) {
            commodity.setCarousel_array(commodity.getCarousel().split(","));
        }
        return commodities;
    }

    /**
     * 根据关键字找商品集合
     *
     * @param search
     * @return
     */
    @Override
    public List<Commodity> findCommodityBySearch(String search) {
        //实现搜索关键词热度排序
        //首先判断这个关键词是否被搜索过
        Double score = redisTemplate.opsForZSet().score("hot_search", search);
        if (score == null) {
            //没有搜索过就把搜索次数设置为1
            score = 1.00;
        } else {
            //如果搜索过则把搜索次数设置+1
            score = score + 1;
        }
        //添加或者更新搜索排行
        redisTemplate.opsForZSet().add("hot_search", search, score);


        //数据库中搜索符合的商品
        search = '%' + search + '%';
        List<Commodity> commodities = commodityMapper.findCommodityBySearch(search);
        for (Commodity commodity : commodities) {
            commodity.setCarousel_array(commodity.getCarousel().split(","));
        }
        return commodities;

    }

    /**
     * 根据商品id和角色找商品
     *
     * @param id
     * @return
     */
    @Override
    public Commodity findCommodityById(Integer id, String role) {
        Commodity commodity = commodityMapper.findCommodityByIdAndRole(id, role);
        commodity.setCarousel_array(commodity.getCarousel().split(","));
        return commodity;
    }

    /**
     * 根据id删除商品
     *
     * @param id
     */
    @Override
    public void deleteCommodity(Integer id) {
        commodityMapper.deleteCommodity(id);
    }

    /**
     * 根据id更新状态码
     *
     * @param id
     */
    @Override
    public void updateStateById(Integer id, Integer state) {

        commodityMapper.updateStateById(id, state);
    }

    /**
     * 根据redis里存的id集合查询商品
     *
     * @return
     */
    @Override
    public List<Commodity> findCommoditiesByRedis(Integer state, String key) {
        List<Commodity> commodities = new ArrayList<Commodity>();
        //当redis中有该key执行查询
        if (redisUtil.hasKey(key)) {
            //从redis中查询用户id集合
            List<Object> ids = redisUtil.lGet(key, 0, -1);
            //根据查询到的用户id集合查询所以满足条件的用户
            commodities = commodityMapper.findCommoditiesByIds(ids, state);
            //将轮播图地址处理为数组方便展示
            for (Commodity commodity : commodities) {
                commodity.setCarousel_array(commodity.getCarousel().split(","));
            }
            return commodities;
        }
        return commodities;
    }

    /**
     * 添加商品到热门商品集合
     * 如果商品不存在则不做操作
     *
     * @param id
     */

    @Override
    public void add_hot_commodity(Integer id) {
        Commodity commodity = commodityMapper.findCommodityById(id);
        //要有这个商品 并且不能是已失效的
        if (commodity != null && commodity.getState() != 2) {
            String key = "hot_commodity";
            redisUtil.lSet(key, id);
        }

    }

    /**
     * 从热门商品集合删除商品
     * 如果商品不存在则不做操作
     *
     * @param id
     */
    @Override
    public void delete_hot_commodity(Integer id) {
        if (commodityMapper.findCommodityById(id) != null) {
            String key = "hot_commodity";
            redisUtil.lRemove(key, 1, id);
        }
    }

    /**
     * 热门搜索关键字
     *
     * @return
     */
    @Override
    public List<String> hot_search() {
        //获取redis里的数据 降序排序
        List<String> searches = new ArrayList<>(redisTemplate.opsForZSet().reverseRange("hot_search", 0, -1));
        if (searches.size() > 10) {//判断list长度是否大于10个再截取，要不然或保错

            searches = searches.subList(0, 10);//取前10条数据，下标包头不包尾

        }
        return searches;
    }

    /**
     * 添加销量
     *
     * @param id
     */
    @Override
    public void addSales(Integer id) {
        commodityMapper.addSales(id);
    }
}
