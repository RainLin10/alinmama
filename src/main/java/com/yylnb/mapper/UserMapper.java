package com.yylnb.mapper;

import com.yylnb.entity.User;
import com.yylnb.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.jdbc.core.SqlProvider;

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
    @Select("SELECT * FROM user WHERE account=${account}")
    User findUserByAccount(@Param("account") String account);

    /**
     * 根据id返回用户的所有信息
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM user_info WHERE user_id=${id}")
    UserInfo findUserInfoByid(@Param("id") Integer id);

    /**
     * 查询所有用户
     *
     * @return
     */
    @Select("SELECT u.account,ui.* FROM user u INNER JOIN user_info ui ON u.id=ui.user_id")
    UserInfo[] findAllUsers();


}
