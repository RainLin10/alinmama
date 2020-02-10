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
    CommodityService commodityService;

    @Test
    public void test() {
        String key = "购买商品ID5的所有用户";
        List<Object> ids = redisUtil.lGet(key, 0, -1);
        System.out.println(ids);
    }
}
