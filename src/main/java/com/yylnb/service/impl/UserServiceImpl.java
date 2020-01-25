package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.UserService;
import com.yylnb.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author RainLin
 * @date 2020/1/25 - 16:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SessionUtil sessionUtil;

    /**
     * 根据controller层包装的user类，将输入的数据和Session中存的数据融合，一起存入数据库，减少SQL的代码
     *
     * @param user
     * @param session
     */

    @Override
    public void updateUserInfoById(User user, HttpSession session) {
        //融合后的user
        User session_user = sessionUtil.merge(user, session);
        //存入数据库
        userMapper.updateUserInfoById(session_user);
        //将新的用户数据存入session
        session.setAttribute("user_info", session_user);
    }
}
