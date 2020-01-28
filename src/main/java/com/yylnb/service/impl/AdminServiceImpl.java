package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.mapper.AdminMapper;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/22 - 16:57
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 查询 按条件筛选后的用户 all=所有 business=卖家 admin=管理员
     *
     * @return
     */
    @Override
    public List<User> findAllUsers(String where) {
        return adminMapper.findAllUsers(where);

    }




}
