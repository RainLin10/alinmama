package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RainLin
 * @date 2020/1/25 - 16:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 根据id更新用户数据
     *
     * @param user
     */
    @Override
    public void updateUserInfoById(User user) {
        userMapper.updateUserInfoById(user);
    }

    /**
     * 根据id更新用户表
     *
     * @param user
     */
    @Override
    public void updateUserById(User user) {
        userMapper.updateUserById(user);
    }


}
