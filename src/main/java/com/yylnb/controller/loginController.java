package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.entity.UserInfo;
import com.yylnb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @author RainLin
 * @date 2020/1/19 - 15:06
 */

@Controller
public class loginController {
    @Autowired
    UserService userService;

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
            //根据id查找到用户的所有信息
            UserInfo userInfo = userService.findUserInfoById(id);
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
        }


    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register() {
        System.out.println("模拟注册");
        return null;
    }
}
