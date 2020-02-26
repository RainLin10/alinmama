package com.yylnb.service.impl;

import com.yylnb.entity.Commodity;
import com.yylnb.entity.Commodity_order;
import com.yylnb.entity.User;
import com.yylnb.mapper.CommodityMapper;
import com.yylnb.mapper.CommodityOrderMapper;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.CommodityOrderService;
import com.yylnb.service.CommodityService;
import com.yylnb.webSocket.LoginAndMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/2 - 20:19
 */
@Service
public class CommodityOrderServiceImpl implements CommodityOrderService {
    @Autowired
    CommodityOrderMapper commodityOrderMapper;
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    LoginAndMessage loginAndMessage;


    /**
     * 某商品的所有购买者
     *
     * @return
     */
    @Override
    public List<Commodity_order> allUsersOnCommodity(Integer commodity_id) {
        List<Commodity_order> commodity_orders = new ArrayList<Commodity_order>();
        if (commodityOrderMapper.findOrdersByCommodity_id(commodity_id) != null) {
            commodity_orders = commodityOrderMapper.findOrdersByCommodity_id(commodity_id);
            for (Commodity_order order : commodity_orders) {
                order.setUser(userMapper.findUserInfoById(order.getUser_id()));
            }
            return commodity_orders;
        }
        return commodity_orders;
    }

    /**
     * 某用户的所有购买商品
     *
     * @return
     */
    @Override
    public List<Commodity_order> allCommoditiesOnUser(Integer user_id, Integer state) {
        List<Commodity_order> commodity_orders = new ArrayList<Commodity_order>();

        if (commodityOrderMapper.findOrdersByUser_idAndState(user_id, state) != null) {
            commodity_orders = commodityOrderMapper.findOrdersByUser_idAndState(user_id, state);
            for (Commodity_order order : commodity_orders) {
                Commodity commodity = commodityMapper.findCommodityById(order.getCommodity_id());
                commodity.setCarousel_array(commodity.getCarousel().split(","));
                order.setCommodity(commodity);
            }
            return commodity_orders;
        }
        return commodity_orders;
    }


    /**
     * 根据订单号找
     *
     * @param id
     * @return
     */
    @Override
    public Commodity_order findOrderById(Integer id) {
        return commodityOrderMapper.findOrderById(id);
    }

    /**
     * 添加订单
     *
     * @param commodity_order
     */
    @Override
    public void insertCommodity_order(Commodity_order commodity_order) {
        commodityOrderMapper.insertCommodity_order(commodity_order);
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Override
    public void deleteCommodity_order(Integer id) {
        commodityOrderMapper.deleteCommodity_order(id);
    }

    /**
     * 根据id改变订单的状态
     *
     * @param id
     */
    public void changeCommodity_orderState(Integer id, Integer state) {
        commodityOrderMapper.changeCommodity_orderState(id, state);
    }

    /**
     * 根据id改变comment_id
     *
     * @param id
     * @param comment_id
     */
    @Override
    public void changeCommentIdById(Integer id, Integer comment_id) {
        commodityOrderMapper.changeCommentIdById(id, comment_id);
    }
}
