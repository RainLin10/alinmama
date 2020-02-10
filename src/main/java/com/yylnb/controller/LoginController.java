package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;


/**
 * @author RainLin
 * @date 2020/1/19 - 15:06
 */

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * 接收前端传来的账户和密码使用shiro进行登录校验,登录成功则存入session相关的用户信息，登陆失败则用model返回相关提示信息
     *
     * @param account
     * @param password
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password, Model model, HttpSession session) {
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account, password);
        //执行登录方法
        try {
            //登录，成功则将查询用户信息后加入session，失败则直接抛出异常
            subject.login(usernamePasswordToken);
            //登录成功
            //获取用户id
            User user = (User) subject.getPrincipal();
            Integer id = user.getId();
            //登录成功后更新相关信息
            userService.updateForLogin(id);
            //根据id查找到用户的所有信息
            User userInfo = userService.findUserInfoById(id);
            //将信息传入session
            session.setAttribute("userInfo", userInfo);
            return "redirect:/";
        } catch (UnknownAccountException e) {
            //用户不存在
            model.addAttribute("msg", "用户不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            //用户不存在
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (UnknownHostException e) {
            model.addAttribute("msg", "找不到主机");
            return "login";
        }


    }

    /**
     * 使用shiro进行注销
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    /**
     * 接收前端传来的账户密码进行注册
     *
     * @param account
     * @param password
     * @param model
     * @return
     * @throws UnknownHostException
     */
    @PostMapping("/register")
    public String register(@RequestParam("account") String account, @RequestParam("password") String password, Model model) throws UnknownHostException {
        String msg = userService.insertUserAndUserInfo(account, password);
        model.addAttribute("msg", msg);
        return "login";
    }
}
