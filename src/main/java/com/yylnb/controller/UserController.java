package com.yylnb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yylnb.entity.Commodity;
import com.yylnb.entity.Commodity_order;
import com.yylnb.entity.User;
import com.yylnb.service.CommodityOrderService;
import com.yylnb.service.CommodityService;
import com.yylnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/25 - 14:11
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CommodityService commodityService;
    @Autowired
    CommodityOrderService commodityOrderService;

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
        //因为是修改自己的信息，所以直接取出session，更新信息并存入数据库
        User userInfo = (User) session.getAttribute("userInfo");
        userInfo.setNick_name(nick_name);
        userInfo.setIntroduction(introduction);
        userInfo.setGender(gender);
        userService.updateUserAndUserInfoById(userInfo);
        session.setAttribute("userInfo", userInfo);
        return "redirect:/user/" + userInfo.getUser_id();
    }

    /**
     * 提交id 申请成为卖家
     *
     * @param id
     * @return
     */
    @GetMapping("/apply_seller")
    public String apply_seller(@RequestParam("id") Integer id) {
        String key = "apply_seller";
        userService.addIdToKey(id, key);
        return "redirect:/";
    }


    /**
     * 我的订单
     * state只能是1出售中或者2已卖出
     *
     * @param pn
     * @param state
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/i_buy/{state}/{pn}")
    public String i_sell(@PathVariable("pn") Integer pn,
                         @PathVariable("state") Integer state,
                         Model model,
                         HttpSession session) {
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);
        //得到用户的id
        User user = (User) session.getAttribute("userInfo");
        Integer user_id = user.getUser_id();

        List<Commodity_order> commodities = commodityOrderService.allCommoditiesOnUser(user_id, state);
        //包装所有商品，获得更多方法，5为显示5个页码
        PageInfo page = new PageInfo(commodities, 10);
        model.addAttribute("commodities", page);
        model.addAttribute("state", state);
        model.addAttribute("whoYou", "user");
        model.addAttribute("title", "我的订单");
        return "my_order";
        //my_commodity.html 需要变量
        //title 页面标题
        //commodities 商品
        //whoYou 角色
        //state 商品状态码
    }

    /**
     * 根据id找用户
     *
     * @param user_id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("{user_id}")
    public String user_info(@PathVariable("user_id") Integer user_id,
                            HttpSession session,
                            Model model) {

        User user = (User) session.getAttribute("userInfo");
        //如果是本机用户 则直接返回Session
        if (user.getUser_id().equals(user_id)) {
            model.addAttribute("userInfo", user);
        } else {
            model.addAttribute("userInfo", userService.findUserInfoById(user_id));
        }
        return "user_info";
    }


    @RequestMapping("/chat/{receiver_id}")
    public String chatOneByOne(@PathVariable("receiver_id") Integer receiver_id,
                               Model model,
                               HttpSession session) {
        User send_user = (User) session.getAttribute("userInfo");
        //不允许自己和自己对话
        if (send_user.getUser_id().equals(receiver_id)) {
            return "redirect:/";
        }
        User receiver_user = userService.findUserInfoById(receiver_id);
        model.addAttribute("send_user", send_user);
        model.addAttribute("receiver_user", receiver_user);
        return "chat";
    }
}
