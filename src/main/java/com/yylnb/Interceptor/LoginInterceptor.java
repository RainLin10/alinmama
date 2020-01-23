package com.yylnb.Interceptor;

import com.yylnb.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RainLin
 * @date 2020/1/18 - 17:02
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 拦截器预处理
     * 检查是否登录，如果登录则不允许再进入登录页面
     * @param request
     * @param response
     * @param handler
     * @return true/false
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user!=null){
            response.sendRedirect("/");
        }
        return true;
    }


}
