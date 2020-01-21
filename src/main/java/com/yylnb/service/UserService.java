package com.yylnb.service;

import com.yylnb.entity.User;
import com.yylnb.entity.UserInfo;

/**
 * @author RainLin
 * @date 2020/1/19 - 15:57
 */
public interface UserService {
    User findUserByAccount(String account);
    UserInfo findUserInfoById(Integer id);
    UserInfo[] findAllUsers();
}
