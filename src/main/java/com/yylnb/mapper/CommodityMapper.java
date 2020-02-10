package com.yylnb.mapper;

import com.yylnb.entity.Commodity;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

/**
 * @author RainLin
 * @date 2020/1/28 - 13:48
 */
@Mapper
public interface CommodityMapper {
    /**
     * 添加商品
     *
     * @param commodity
     */
    @Insert("INSERT INTO commodity(user_id,name,nick_name,introduction,price,carousel) VALUES(#{user_id},#{name},#{nick_name},#{introduction},#{price},#{carousel})")
    void insertCommodity(Commodity commodity);

    /**
     * 根据用户的id和商品的状态码返回商品
     *
     * @param user_id
     * @param state
     * @return
     */
    @Select("SELECT * FROM commodity WHERE user_id=#{user_id} and state=#{state}")
    List<Commodity> findCommoditiesByUserIdAndState(@Param("user_id") Integer user_id, @Param("state") Integer state);

    /**
     * 根据状态码返回商品
     *
     * @param state
     * @return
     */
    @Select("SELECT * FROM commodity WHERE  state=#{state}")
    List<Commodity> findCommoditiesByState(@Param("state") Integer state);

    /**
     * 根据商品id和角色查找商品
     * 用户只能查询状态码处于1 2 的商品 其他角色不受限制
     *
     * @param id
     * @return
     */

    Commodity findCommodityByIdAndRole(@Param("id") Integer id, @Param("role") String role);

    /**
     * 根据商品id删除商品
     *
     * @param id
     */
    @Delete("DELETE FROM commodity WHERE id=#{id}")
    void deleteCommodity(@Param("id") Integer id);

    /**
     * 根据id更新状态码
     *
     * @param id
     * @param state
     */
    @Update("UPDATE commodity SET state=#{state}  WHERE id=#{id}")
    void updateStateById(@Param("id") Integer id, @Param("state") Integer state);

    /**
     * 用redis从存的id集合找符合的商品集合
     *
     * @param ids
     * @param state
     * @return
     */
    List<Commodity> findCommoditiesByIds(@Param("ids") List<Object> ids, @Param("state") Integer state);

    /**
     * 根据商品id查询商品
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM commodity WHERE id = #{id}")
    Commodity findCommodityById(@Param("id") Integer id);

    /**
     * 根据商品id和状态码查询商品
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM commodity WHERE id = #{id} AND state=#{state}")
    Commodity findCommodityByIdAndState(@Param("id") Integer id, @Param("state") Integer state);


    /**
     * 根据关键字查询商品集合
     *
     * @param search
     * @return
     */
    @Select("SELECT * FROM commodity WHERE name like #{search} or  introduction like #{search}")
    List<Commodity> findCommodityBySearch(@Param("search") String search);

    /**
     * 根据id增加销量
     *
     * @param id
     */
    @Update("UPDATE commodity SET sales=sales+1 WHERE id = #{id}")
    void addSales(Integer id);
}
