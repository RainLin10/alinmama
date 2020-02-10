package com.yylnb.mapper;

import com.yylnb.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * @author RainLin
 * @date 2020/1/19 - 15:53
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名返回用户id、用户名、密码
     *
     * @param account
     * @return
     */
    User findUserByAccount(@Param("account") String account);

    /**
     * 根据id返回用户的所有信息
     *
     * @param id
     * @return
     */
    User findUserInfoById(@Param("id") Integer id);


    /**
     * 往两张表注册用户(用户表、用户信息表)
     *
     * @param user
     */
    void insertUser(User user);


    void insertUserInfo(User userInfo);


    /**
     * 每次登录更新登录时间、登录次数、登录ip
     *
     * @param user
     */
    void updateUser_login(User user);


    /**
     * 根据user_id，更新
     * 昵称、是否卖家、性别、登陆时间、登录次数、登录ip、个人介绍、头像
     *
     * @param user
     */
    void updateUserInfoById(User user);

    /**
     * 根据id更新user表
     *
     * @param user
     */

    void updateUserById(User user);


    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> findAllUsers(@Param("role") String role);

    /**
     * 根据redis中查询的id集合查找用户
     *
     * @param ids
     * @return
     */
    List<User> findUsersByIds(@Param("ids") List<Object> ids);


}
