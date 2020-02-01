package com.yylnb.service.impl;

import com.yylnb.entity.User;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.UserService;
import com.yylnb.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author RainLin
 * @date 2020/1/25 - 16:35
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 根据id更新用户数据
     *
     * @param user
     */
    @Override
    public void updateUserAndUserInfoById(User user) {
        if (user.getId() != null || user.getAccount() != null) {
            userMapper.updateUserById(user);
        }
        if (user.getUser_id() != null) {
            userMapper.updateUserInfoById(user);
        }
    }

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
        log.info("用户id:" + id + "登录了");
        User userInfo = new User();
        userInfo.setUser_id(id);
        userInfo.setLogin_time(System.currentTimeMillis());
        userInfo.setLogin_ip(InetAddress.getLocalHost().getHostAddress());
        userMapper.updateUser_login(userInfo);
    }

    /**
     * 查询 按条件筛选后的用户 all=所有 business=卖家 admin=管理员
     *
     * @return
     */
    @Override
    public List<User> findAllUsers(String role) {
        return userMapper.findAllUsers(role);

    }


    /**
     * 根据key在redis中查询id集合，用id集合去数据库查找用户
     *
     * @param key
     * @return
     */
    @Override
    public List<User> findAllUsersByRedis(String key) {
        //从redis中查询正在申请卖家资格的用户
        Set<Object> ids = redisUtil.sGet(key);
        List<User> users = new ArrayList<User>();
        //当redis中有该key执行查询
        if (redisUtil.hasKey(key)) {
            //根据查询到的用户id集合查询所以满足条件的用户
            users = userMapper.findUsersByIds(ids);
            return users;
        }
        return users;
    }

    /**
     * 失败或者退回卖家操作请求
     *
     * @param result
     * @param user_id
     */
    @Override
    public void seller_passOrLose(String result, Integer user_id) {
        //不管是通过还是退回都需要将redis中的id删除
        redisUtil.setRemove("apply_seller", user_id);
        if (result.equals("pass")) {
            //如果是通过还需要改角色
            User user = new User();
            user.setRole("seller");
            user.setId(user_id);
            userMapper.updateUserById(user);
            log.info("用户id:" + user_id + "成为了卖家");
        } else if (result.equals("lose")) {
            log.info("用户id:" + user_id + "申请卖家资格被退回");
        }
    }

    /**
     * 对指定key增加id
     *
     * @param id
     * @param key
     */
    @Override
    public void addIdToKey(Integer id, String key) {
        log.info("用户id:" + id + "正在申请卖家资格");
        redisUtil.sSet(key, id);
    }
}
