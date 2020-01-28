package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /**
     * 在个人信息页面更改用户数据
     *
     * @param nick_name
     * @param introduction
     * @param gender
     * @param session
     * @return
     */
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("nick_name") String nick_name,
                                 @RequestParam("introduction") String introduction,
                                 @RequestParam("gender") String gender,
                                 HttpSession session,
                                 Model model) {
        if (nick_name.equals("")) {
            model.addAttribute("msg", "用户名不能为空");
        }
        //因为是修改自己的信息，所以直接取出session，更新信息并存入数据库
        User userInfo = (User) session.getAttribute("userInfo");
        userInfo.setNick_name(nick_name);
        userInfo.setIntroduction(introduction);
        userInfo.setGender(gender);
        userService.updateUserInfoById(userInfo);
        session.setAttribute("userInfo", userInfo);
        return "redirect:/user/user_info";
    }
}
