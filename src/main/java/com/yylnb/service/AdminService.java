package com.yylnb.service;

import com.yylnb.entity.User;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/22 - 16:57
 */
public interface AdminService {

    //查询 按条件筛选后的用户 all=所有 business=卖家 admin=管理员 service=客服
    List<User> findAllUsers(String where);


}
