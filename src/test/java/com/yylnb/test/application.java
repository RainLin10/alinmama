package com.yylnb.test;

import com.yylnb.entity.User;
import com.yylnb.service.CommodityService;
import com.yylnb.service.UserService;
import com.yylnb.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * @author RainLin
 * @date 2020/1/17 - 15:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class application {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    @Test
    public void test() {
        Integer i = 10;
        Set<Object> ids = redisUtil.sGet(i+"commodity");
        System.out.println(ids);
    }
}
