package com.yylnb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author RainLin
 * @date 2020/1/17 - 15:07
 */

/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 *  它由以下注解联合组成
 * @Target(ElementType.TYPE)
 * @Retention(RetentionPolicy.RUNTIME)
 * @Documented
 * @Inherited
 * @SpringBootConfiguration 标注在某个类上，表示这是一个Spring Boot的配置类
 * @EnableAutoConfiguration 开启自动配置功能
 * @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
 *                @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
 */
@SpringBootApplication
@MapperScan("com.yylnb.mapper")
@EnableRabbit//开启rabbit注解
public class MainApplication {
    public static void main(String[] args) {

        // Spring应用启动起来
        SpringApplication.run(MainApplication.class,args);
    }
}
