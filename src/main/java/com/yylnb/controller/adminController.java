package com.yylnb.controller;

import com.yylnb.entity.UserInfo;
import com.yylnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/20 - 13:45
 */
@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    UserService userService;

    @RequestMapping("/findAllUsers")
    public String findAllUsers(Model model) {
        List<UserInfo> userInfos = Arrays.asList(userService.findAllUsers());
        model.addAttribute("users", userInfos);
        return "admin";
    }
}
