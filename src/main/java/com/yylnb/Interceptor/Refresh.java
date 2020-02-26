package com.yylnb.Interceptor;

import com.yylnb.entity.User;
import com.yylnb.webSocket.LoginAndMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RainLin
 * @date 2020/2/24 - 19:31
 */
public class Refresh implements HandlerInterceptor {
    @Autowired
    LoginAndMessage loginAndMessage;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Integer onlineCount = loginAndMessage.getOnlineCount();
        request.getSession().setAttribute("onlineCount", onlineCount);
        return true;
    }
}
