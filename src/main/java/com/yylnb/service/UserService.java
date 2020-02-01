package com.yylnb.service;

import com.yylnb.entity.User;

import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/25 - 16:34
 */
public interface UserService {
    void updateUserAndUserInfoById(User user);//更新用户表和用户信息表

    User findUserByAccount(String account);//账户名找用户

    User findUserInfoById(Integer id);//id找用户

    String insertUserAndUserInfo(String account, String password) throws UnknownHostException;//注册

    void updateForLogin(Integer id) throws UnknownHostException;//登陆时更新

    List<User> findAllUsers(String role);//查询 按条件筛选后的用户 all=所有 seller=卖家 admin=管理员 service=客服

    List<User> findAllUsersByRedis(String key);//根据key在redis中查询id集合，用id集合去数据库查找用户

    void seller_passOrLose(String result, Integer user_id);

    void addIdToKey(Integer id, String key);

}
