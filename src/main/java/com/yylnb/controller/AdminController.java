package com.yylnb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yylnb.entity.User;
import com.yylnb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/20 - 13:45
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    /**
     * 查询所有用户，进入admin.html的初始数据
     *
     * @param model
     * @return
     */
    @RequestMapping("/findAllUsers/{role}/{pn}")
    public String findAllUsers(@PathVariable("pn") Integer pn,
                               @PathVariable("role") String role,
                               Model model) {
        //使用分页插件，pn是页面传来的页码，pageSize:一页多少个数据
        PageHelper.startPage(pn, 12);
        //获取所有用户
        List<User> userInfos = userService.findAllUsers(role);
        //包装所有员工，获得更多方法，navigatePages:页码的显示个数
        PageInfo page = new PageInfo(userInfos, 10);
        model.addAttribute("users", page);
        model.addAttribute("role", role);
        model.addAttribute("title", "管理员页面");
        model.addAttribute("whoYou", "admin");
        return "user";
    }

    /**
     * 在管理员页面更新用户信息
     *
     * @return
     */
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("user_id") Integer user_id,
                                 @RequestParam("nick_name") String nick_name,
                                 @RequestParam("gender") String gender,
                                 @RequestParam("role") String role,
                                 @RequestParam("page") String page,//因为和role重名改为page 作用是在操作完成回到操作时的页面
                                 @RequestParam("pn") Integer pn,//操作完成回到操作时的页码
                                 HttpSession session,
                                 Model model) {
        if (nick_name.equals("")) {
            model.addAttribute("msg", "用户名不能为空");
        }
        User user = new User();
        user.setUser_id(user_id);
        user.setNick_name(nick_name);
        user.setGender(gender);
        user.setId(user_id);
        user.setRole(role);
        userService.updateUserAndUserInfoById(user);


        //如果更改的是自己的信息 则更新session、[不必要代码！]
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if (user_id.equals(u.getId())) {
            User userInfo = (User) session.getAttribute("userInfo");
            userInfo.setNick_name(nick_name);
            userInfo.setGender(gender);
            userInfo.setRole(role);
            session.setAttribute("userInfo", userInfo);
        }
        return "redirect:/admin/findAllUsers/" + page + "/" + pn;
    }


}
