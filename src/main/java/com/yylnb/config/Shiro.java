package com.yylnb.config;

import com.yylnb.shiro.RoleFilter;
import com.yylnb.shiro.UserRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author RainLin
 * @date 2020/1/17 - 16:30
 */
@Configuration
public class Shiro {
    /**
     * ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 自定义的filter 让多角色从并变为或 [admin,user]默认需要两个权限满足，现在时任意满足一个即可访问
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("roles", new RoleFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        //添加内置过滤器
        /**
         * 常用内置过滤器
         * anon:无需认证可以访问
         * authc:必须认证才能访问
         * user:如果使用rememberMe的功能可以直接访问
         * perms:该资源必须得当资源权限才可以访问
         * roles:该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        //转发请求跳转页面直接放行-----------------------------------
        //根目录
        filterMap.put("/", "anon");
        //去主页
        filterMap.put("/index", "anon");
        //去登陆页面和登录
        filterMap.put("/to_login", "anon");
        filterMap.put("/login", "anon");
        //注册
        filterMap.put("/register", "anon");
        //注销
        filterMap.put("/logout", "logout");
        //过渡页面
        filterMap.put("/transition", "anon");
        //静态资源 设置为无需认证------------------------------------
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/imgs/**", "anon");
        //用户上传的头像文件夹
        filterMap.put("/alinmama_avatar/**", "anon");
        //用户上传的商品文件夹
        filterMap.put("/alinmama_commodity/**", "anon");
        //角色分类--------------------------------------------------
        //管理员
        filterMap.put("/admin/**", "roles[admin]");
        //卖家
        filterMap.put("/seller/**", "roles[seller]");
        //购物
        filterMap.put("/apply/**", "roles[user,seller]");
        //客服
        filterMap.put("/service/**", "roles[service,admin]");
        //用户操作
        filterMap.put("/user/**", "roles[user,admin,seller,service]");
        //-----------------------------------------------------------
        //因为设置权限会从上覆盖下面,所以可以除去上面配置的请求，其他请求全部需要认证
        filterMap.put("/**", "authc");
        //-----------------------------------------------------------
        //加载权限
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //设置默认页面------------------------------------------------
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/to_login");
        //设置无权限页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
        //-----------------------------------------------------------
        return shiroFilterFactoryBean;
    }

    /**
     * DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm, @Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }


    /**
     * Session Manager：会话管理
     * 即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；
     * 会话可以是普通JavaSE环境的，也可以是如Web环境的；
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间、是在没有任何访问的情况后开始计算
        // 经过测试 删除时也会删除自己设置的session [session.setAttribute("userInfo", userInfo);]
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 24);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
}
