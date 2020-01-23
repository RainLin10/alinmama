package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author RainLin
 * @date 2020/1/19 - 15:59
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名返回用户id、用户名、密码
     *
     * @param account
     * @return
     */
    @Override
    public User findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

    /**
     * 根据id返回用户的所有信息
     *
     * @param id
     * @return
     */
    @Override
    public User findUserInfoById(Integer id) {
        return userMapper.findUserInfoById(id);
    }


    /**
     * 接收账户密码，并设置初始头像、姓名等信息注册进数据库
     *
     * @param account
     * @param password
     */
    @Override
    @Transactional//事务注解，因为要插入两张表，所以必须保证要么一起成功，要么一起失败
    public String insertUserAndUserInfo(String account, String password) throws UnknownHostException {
        //在注册之前先查询数据库中是否有相同的账户名
        User isUser = userMapper.findUserByAccount(account);
        if (isUser != null) {
            return "账户已被注册";
        }
        User user = new User();
        User userInfo = new User();
        //往user表存入用户
        user.setAccount(account);
        user.setPassword(password);
        user.setRole("user");
        userMapper.insertUser(user);
        //往user_info表存入用户
        userInfo.setUser_id(userMapper.findUserByAccount(account).getId());
        userInfo.setNick_name("用户" + System.currentTimeMillis() / 1000);
        userInfo.setAvatar("/imgs/avatar.jpg");
        userInfo.setRegister_time(System.currentTimeMillis());
        userInfo.setLogin_time(System.currentTimeMillis());
        userInfo.setLogin_times(1);
        userInfo.setLogin_ip(InetAddress.getLocalHost().getHostAddress());
        userMapper.insertUserInfo(userInfo);
        return "注册成功";
    }

    /**
     * 接收id，生成当前时间、ip进行用户数据更新
     *
     * @param id
     * @throws UnknownHostException
     */
    @Override
    public void updateForLogin(Integer id) throws UnknownHostException {
        User userInfo = new User();
        userInfo.setUser_id(id);
        userInfo.setLogin_time(System.currentTimeMillis());
        userInfo.setLogin_ip(InetAddress.getLocalHost().getHostAddress());
        userMapper.updateUser_login(userInfo);
    }
}
