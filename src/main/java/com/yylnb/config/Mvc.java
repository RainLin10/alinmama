package com.yylnb.config;

import com.yylnb.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author RainLin
 * @date 2020/1/17 - 16:30
 */
@Configuration
public class Mvc implements WebMvcConfigurer {
    /**
     * 视图跳转器
     * 用于页面的跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         * 将指定请求转发到指定页面
         * "/"默认是转发到"/index.html"
         */
        //接收从shiro配置文件里shiroFilterFactoryBean.setLoginUrl("/login");的请求,让/login转发到login.html
        registry.addViewController("/to_login").setViewName("login.html");
        //"/"默认是转发到"/index.html",但是"/index"并没有默认是转发到"/index.html"所以要配置以下
        registry.addViewController("/index").setViewName("index.html");
        //这是当用户没有访问权限时跳转的页面
        registry.addViewController("/unAuth").setViewName("unAuth.html");
        //进入个人信息页面
        registry.addViewController("/user_info").setViewName("user_info.html");

    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 配置一个检查是否登录的拦截器，只拦截去登录页面和登录的请求
         * 具体的处理方案由LoginInterceptor执行
         */
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/to_login", "/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射头像文件夹的路径
        registry.addResourceHandler("/alinmam_avatar/**").addResourceLocations("file:D:/alinmam_avatar/");
    }

}
