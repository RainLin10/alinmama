package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.entity.UserInfo;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RainLin
 * @date 2020/1/19 - 15:59
 */
@Service
public class UserServiceImpl implements UserService {
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
    public UserInfo findUserInfoById(Integer id) {
        return userMapper.findUserInfoByid(id);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public UserInfo[] findAllUsers() {
        return userMapper.findAllUsers();
    }
}
