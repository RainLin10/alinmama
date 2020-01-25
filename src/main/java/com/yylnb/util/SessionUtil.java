package com.yylnb.util;

import com.yylnb.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author RainLin
 * @date 2020/1/25 - 19:12
 */
@Component
public class SessionUtil {
    /**
     * 将前端接收到的用户数据插入到Session里获取的用户数据里面，方便存入数据库
     * @param user
     * @param session
     * @return
     */
    public User merge(User user, HttpSession session){
        User session_user = (User) session.getAttribute("userInfo");
        if(user.getId()!=null){
            session_user.setId(user.getId());
        }
        if(user.getAccount()!=null){
            session_user.setAccount(user.getAccount());
        }
        if(user.getPassword()!=null){
            session_user.setPassword(user.getPassword());
        }
        if(user.getRole()!=null){
            session_user.setRole(user.getRole());
        }
        if(user.getUser_id()!=null){
            session_user.setUser_id(user.getUser_id());
        }
        if(user.getNick_name()!=null){
            session_user.setNick_name(user.getNick_name());
        }
        if(user.getIntroduction()!=null){
            session_user.setIntroduction(user.getIntroduction());
        }
        if(user.getIsBusiness()!=null){
            session_user.setIsBusiness(user.getIsBusiness());
        }
        if(user.getGender()!=null){
            session_user.setGender(user.getGender());
        }
        if(user.getAvatar()!=null){
            session_user.setAvatar(user.getAvatar());
        }
        if(user.getRegister_time()!=null){
            session_user.setRegister_time(user.getRegister_time());
        }
        if(user.getLogin_time()!=null){
            session_user.setLogin_time(user.getLogin_time());
        }
        if(user.getLogin_times()!=null){
            session_user.setLogin_times(user.getLogin_times());
        }
        if(user.getLogin_ip()!=null){
            session_user.setLogin_ip(user.getLogin_ip());
        }

        return  session_user;
    }
}
