package com.yylnb.mapper;

import com.yylnb.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/22 - 16:55
 */
@Mapper
public interface AdminMapper {
    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> findAllUsers(@Param("where") String where);



}
