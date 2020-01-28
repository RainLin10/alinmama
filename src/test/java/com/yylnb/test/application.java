package com.yylnb.test;

import com.yylnb.config.RabbitMQ;
import com.yylnb.entity.User;
import com.yylnb.mapper.AdminMapper;
import com.yylnb.mapper.UserMapper;
import com.yylnb.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    public void test() {
        System.out.println(ClassUtils.getDefaultClassLoader().getResource("static").getPath());
    }
}
