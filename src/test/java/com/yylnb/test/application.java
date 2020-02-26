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

import java.util.*;

/**
 * @author RainLin
 * @date 2020/1/17 - 15:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class application {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CommodityService commodityService;

    @Test
    public void test() {
        List<String> b = new ArrayList<String>();
        b.add("1");
        b.add("2");
        b.add("3");
        System.out.println(b);
        b.set(0, null);
        b.set(1, null);
        b.removeAll(Collections.singleton(null));
        System.out.println(b);

    }
}
