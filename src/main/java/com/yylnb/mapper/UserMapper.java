package com.yylnb.mapper;

import com.yylnb.entity.User;
import org.apache.ibatis.annotations.*;

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
    @Select("SELECT * FROM user WHERE account='${account}'")
    User findUserByAccount(@Param("account") String account);

    /**
     * 根据id返回用户的所有信息
     *
     * @param id
     * @return
     */
    @Select("SELECT ui.*,u.role,u.account FROM user_info ui INNER JOIN user u ON ui.user_id = u.id WHERE user_id=${id}")
    User findUserInfoById(@Param("id") Integer id);


    /**
     * 往两张表注册用户(用户表、用户信息表)
     *
     * @param user
     */
    @Insert("INSERT INTO user(account,password,role) VALUES(#{account},#{password},#{role})")
    void insertUser(User user);


    @Insert("INSERT INTO user_info(user_id,nick_name,avatar,register_time,login_time,login_times,login_ip) VALUES(#{user_id},#{nick_name},#{avatar},#{register_time},#{login_time},#{login_times},#{login_ip})")
    void insertUserInfo(User userInfo);


    /**
     * 每次登录更新登录时间、登录次数、登录ip
     *
     * @param user
     */
    @Update("UPDATE user_info SET login_time=#{login_time},login_times=login_times+1,login_ip=#{login_ip} WHERE user_id=#{user_id}")
    void updateUser_login(User user);


    /**
     * 根据user_id，更新
     * 昵称、是否卖家、性别、登陆时间、登录次数、登录ip、个人介绍、头像
     * @param user
     */
    @Update("UPDATE user_info SET nick_name=#{nick_name},isBusiness=#{isBusiness},gender=#{gender},login_time=#{login_time}," +
            "login_times=#{login_times},login_ip=#{login_ip},introduction=#{introduction},avatar=#{avatar} WHERE user_id=#{user_id}")
    void updateUserInfoById(User user);

    /**
     * 根据account，更新
     * 昵称、是否卖家、性别、登陆时间、登录次数、登录ip、个人介绍、头像
     * @param user
     */
    @Update("UPDATE user_info SET nick_name=#{nick_name},isBusiness=#{isBusiness},gender=#{gender},login_time=#{login_time}," +
            "login_times=#{login_times},login_ip=#{login_ip},introduction=#{introduction},avatar=#{avatar} WHERE account=#{account}")
    void updateUserInfoByAccount(User user);

}
