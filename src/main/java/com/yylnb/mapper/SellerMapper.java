package com.yylnb.mapper;

import com.yylnb.entity.Commodity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author RainLin
 * @date 2020/1/28 - 13:48
 */
@Mapper
public interface SellerMapper {
    @Insert("INSERT INTO commodity(user_id,name,introduction,price,carousel) VALUES(#{user_id},#{name},#{introduction},#{price},#{carousel})")
    void insertCommodity(Commodity commodity);
}
