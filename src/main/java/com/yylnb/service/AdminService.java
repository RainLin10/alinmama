package com.yylnb.service;

import com.yylnb.entity.User;

/**
 * @author RainLin
 * @date 2020/1/22 - 16:57
 */
public interface AdminService {
    User[] findAllUsers(String where);//查询所有用户


}
