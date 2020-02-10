package com.yylnb.service;

import com.yylnb.entity.Commodity;
import com.yylnb.entity.Commodity_order;
import com.yylnb.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/2 - 20:19
 */
public interface CommodityOrderService {

    List<Commodity_order> allUsersOnCommodity(Integer commodity_id);


    List<Commodity_order> allCommoditiesOnUser(Integer user_id, Integer state);

    Commodity_order findOrderById(Integer id);

    void insertCommodity_order(Commodity_order commodity_order);

    void deleteCommodity_order(Integer id);

    void changeCommodity_orderState(Integer id, Integer state);

    void changeCommentIdById(Integer id, Integer comment_id);
}
