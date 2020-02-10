package com.yylnb.mapper;


import com.yylnb.entity.Commodity;
import com.yylnb.entity.Commodity_order;
import com.yylnb.entity.User;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author RainLin
 * @date 2020/1/28 - 13:48
 */
@Mapper
public interface CommodityOrderMapper {
    @Select("SELECT * FROM commodity_order WHERE commodity_id=#{commodity_id}")
    List<Commodity_order> findOrdersByCommodity_id(Integer commodity_id);

    @Select("SELECT * FROM commodity_order WHERE user_id=#{user_id}")
    List<Commodity_order> findOrdersByUser_id(Integer user_id);

    @Select("SELECT * FROM commodity_order WHERE user_id=#{user_id} AND state=#{state}")
    List<Commodity_order> findOrdersByUser_idAndState(Integer user_id, Integer state);

    @Select("SELECT * FROM commodity_order WHERE id=#{id}")
    Commodity_order findOrderById(Integer id);

    @Insert("INSERT INTO commodity_order(user_id,commodity_id,seller_id,create_time,quantity,price) VALUES(#{user_id},#{commodity_id},#{seller_id},#{create_time},#{quantity},#{price})")
    void insertCommodity_order(Commodity_order commodity_order);

    @Delete("DELETE FROM commodity_order WHERE id=#{id}")
    void deleteCommodity_order(Integer id);

    @Update("UPDATE commodity_order SET state=#{state} WHERE id = #{id}")
    void changeCommodity_orderState(Integer id, Integer state);

    @Update("UPDATE commodity_order SET comment_id=#{comment_id} WHERE id = #{id}")
    void changeCommentIdById(Integer id, Integer comment_id);

}
