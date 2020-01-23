package com.yylnb.shiro;

import com.yylnb.entity.User;
import com.yylnb.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 *
 * @author RainLin
 * @date 2020/1/19 - 13:43
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    LoginService loginService;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据用户在数据库设置的权限赋予相应的权限
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String role = user.getRole();
        //给用户授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(role);
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //取出用户名
        String account = usernamePasswordToken.getUsername();
        //根据用户名去数据找用户相关信息
        User user = loginService.findUserByAccount(account);
        if (user == null) {
            //用户不存在则返回空，前台会报无用户异常
            return null;
        }
        /**
         * SimpleAuthenticationInfo在第二个参数传入数据库的密码，会自动校验正确性并返回
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
