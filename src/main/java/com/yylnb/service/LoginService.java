package com.yylnb.service;

import com.yylnb.entity.User;

import java.net.UnknownHostException;

/**
 * @author RainLin
 * @date 2020/1/19 - 15:57
 */
public interface LoginService {
    User findUserByAccount(String account);//账户名找用户

    User findUserInfoById(Integer id);//id找用户

    String insertUserAndUserInfo(String account, String password) throws UnknownHostException;//注册

    void updateForLogin(Integer id) throws UnknownHostException;//登陆时更新
}
