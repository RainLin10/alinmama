package com.yylnb.config;

import com.yylnb.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author RainLin
 * @date 2020/1/17 - 16:30
 */
@Configuration
public class Mvc implements WebMvcConfigurer {
    public final static String IMGINPUT = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + "/upload/";

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
        //这是当用户没有访问权限时跳转的页面
        registry.addViewController("/unAuth").setViewName("public/unAuth.html");
        //进入货物上架页面
        registry.addViewController("/seller/add_commodity").setViewName("add_commodity.html");
        //过渡页面
        registry.addViewController("/transition").setViewName("public/transition.html");

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
        registry.addResourceHandler("/alinmama_avatar/**").addResourceLocations("file://" + IMGINPUT + "alinmama_avatar/");
        //映射商品文件夹的路径
        registry.addResourceHandler("/alinmama_commodity/**").addResourceLocations("file://" + IMGINPUT + "alinmama_commodity/");
    }

}
