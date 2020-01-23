package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/20 - 13:45
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 查询所有用户，进入admin.html的初始数据
     *
     * @param model
     * @return
     */
    @RequestMapping("/findAllUsers")
    public String findAllUsers(@RequestParam("where") String where,Model model) {
        List<User> userInfos = Arrays.asList(adminService.findAllUsers(where));
        model.addAttribute("users", userInfos);
        return "admin";
    }

    /**
     * 在管理员页面更新用户信息
     *
     * @return
     */
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("nick_name") String nick_name,@RequestParam("gender") String gender,@RequestParam("isbusiness") String isbusiness) {

        return "redirect:/admin/findAllUsers";
    }


}
