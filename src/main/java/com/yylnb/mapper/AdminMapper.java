package com.yylnb.mapper;

import com.yylnb.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT u.account,u.role,ui.* FROM user u INNER JOIN user_info ui ON u.id=ui.user_id")
    User[] findAllUsers();

    /**
     * 查询所有商家
     *
     * @return
     */
    @Select("SELECT u.account,u.role,ui.* FROM user u INNER JOIN user_info ui ON u.id=ui.user_id WHERE isBusiness='是'")
    User[] findAllBusiness();

    /**
     * 查询所有管理员
     *
     * @return
     */
    @Select("SELECT u.account,u.role,ui.* FROM user u INNER JOIN user_info ui ON u.id=ui.user_id WHERE u.role = 'admin'")
    User[] findAllAdmin();
}
