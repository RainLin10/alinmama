package com.yylnb.service;

import com.yylnb.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @author RainLin
 * @date 2020/1/25 - 16:34
 */
public interface UserService {
    void updateUserInfoById(User user);

    void updateUserById(User user);
}
