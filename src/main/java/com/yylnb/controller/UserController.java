package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author RainLin
 * @date 2020/1/25 - 14:11
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("nick_name") String nick_name,
                                 @RequestParam("introduction") String introduction,
                                 @RequestParam("gender") String gender,
                                 HttpSession session) {
        User user = new User();
        user.setNick_name(nick_name);
        user.setIntroduction(introduction);
        user.setGender(gender);
        userService.updateUserInfoById(user,session);
        return "redirect:/user_info";
    }
}
