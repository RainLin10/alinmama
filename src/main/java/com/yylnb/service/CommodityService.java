package com.yylnb.service;

import com.yylnb.entity.Commodity;
import com.yylnb.entity.User;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/28 - 13:45
 */
public interface CommodityService {
    void insertCommodity(Commodity commodity);//上传商品

    List<Commodity> findCommoditiesByUserIdAndState(Integer user_id, Integer state);//根据用户id和状态码 查找商品

    List<Commodity> findCommoditiesByState(Integer state);//根据状态码找商品集合

    List<Commodity> findCommodityBySearch(String search);//根据关键字找商品集合

    Commodity findCommodityById(Integer id, String role);//根据商品id找商品

    void deleteCommodity(Integer id);//根据id删除商品

    void updateStateById(Integer id, Integer state);//根据id改变商品状态码

    List<Commodity> findCommoditiesByRedis(Integer state, String key);//根据自己的id查询我的订单里的商品

    void add_hot_commodity(Integer id);//添加商品到热门商品集合

    void delete_hot_commodity(Integer id);//从热门商品集合删除商品

    List<String> hot_search();//热搜关键字

    void addSales(Integer id);//添加销量

}
