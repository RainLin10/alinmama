package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.mapper.AdminMapper;
import com.yylnb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RainLin
 * @date 2020/1/22 - 16:57
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public User[] findAllUsers(String where) {
        if (where.equals("all")) {
            return adminMapper.findAllUsers();
        } else if (where.equals("business")) {
            return adminMapper.findAllBusiness();
        } else if (where.equals("admin")) {
            return adminMapper.findAllAdmin();
        }
        return adminMapper.findAllUsers();

    }


}
