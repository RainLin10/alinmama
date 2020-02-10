package com.yylnb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yylnb.entity.Commodity;
import com.yylnb.entity.User;
import com.yylnb.service.CommodityService;
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
 * @date 2020/1/29 - 13:35
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    UserService userService;
    @Autowired
    CommodityService commodityService;

    /**
     * 查询所有满足key的用户，进入user.html的初始数据
     *
     * @return
     */
    @RequestMapping("/findAllUsers/{key}/{pn}")
    public String findAllUsers(@PathVariable("pn") Integer pn,
                               @PathVariable("key") String key,//用作在redis查找用户集合的key
                               Model model) {
        //使用分页插件，pn是页面传来的页码，pageSize:一页多少个数据
        PageHelper.startPage(pn, 12);
        List<User> userInfos = userService.findAllUsersByRedis(key);
        //包装所有用户，获得更多方法，navigatePages:页码的显示个数
        PageInfo page = new PageInfo(userInfos, 10);
        model.addAttribute("users", page);
        model.addAttribute("key", key);
        model.addAttribute("title", "客服页面");
        model.addAttribute("whoYou", "service");
        return "user";
    }

    /**
     * 在客服页面通过或者退回卖家申请
     *
     * @return
     */
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("user_id") Integer user_id,
                                 @RequestParam("pn") Integer pn,//操作完成回到操作时的页码
                                 @RequestParam("key") String key,
                                 @RequestParam("result") String result,
                                 HttpSession session,
                                 Model model) {
        userService.seller_passOrLose(result, user_id);
        return "redirect:/service/findAllUsers/" + key + "/" + pn;
    }

    /**
     * @param pn      进入商品审核页面 数据初始化
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/check/{pn}")
    public String check(@PathVariable("pn") Integer pn,
                        Model model,
                        HttpSession session) {
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);

        //得到商品
        List<Commodity> commodities = commodityService.findCommoditiesByState(0);
        //包装所有商品，获得更多方法，5为显示5个页码
        PageInfo page = new PageInfo(commodities, 10);
        model.addAttribute("commodities", page);
        model.addAttribute("state", 0);
        model.addAttribute("whoYou", "service");
        model.addAttribute("title", "审核商品");
        return "my_commodity";
        //my_commodity.html 需要变量
        //title 页面标题
        //commodities 商品
        //whoYou 角色
        //state 商品状态码
    }

    /**
     * @param pn      进入商品审核页面 数据初始化
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/hotCommodity/{pn}")
    public String hotCommodity(
            @PathVariable("pn") Integer pn,
            Model model,
            HttpSession session) {
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);

        String key = "hot_commodity";
        List<Commodity> commodities = commodityService.findCommoditiesByRedis(1, key);
        //包装所有商品，获得更多方法，5为显示5个页码
        PageInfo page = new PageInfo(commodities, 10);

        model.addAttribute("commodities", page);
        model.addAttribute("state", 1);
        model.addAttribute("whoYou", "service");
        model.addAttribute("title", "热门商品");
        return "my_commodity";
        //my_commodity.html 需要变量
        //title 页面标题
        //commodities 商品
        //whoYou 角色
        //state 商品状态码
    }

    /**
     * 接收id删除商品
     *
     * @param id
     * @param pn 在转发时注入
     * @return
     */
    @GetMapping("/deleteCommodity")
    public String deleteCommodity(@RequestParam("id") Integer id,
                                  @RequestParam("pn") Integer pn) {
        commodityService.deleteCommodity(id);
        return "redirect:/service/check/" + pn;
    }



    /**
     * 接收id让审核中的商品上线
     *
     * @param id
     * @param pn 在转发时注入
     * @return
     */
    @GetMapping("/passCommodity")
    public String passCommodity(@RequestParam("id") Integer id,
                                @RequestParam("pn") Integer pn) {

        commodityService.updateStateById(id, 1);
        return "redirect:/service/check/" + pn;
    }


    @RequestMapping("/add_or_delete_hot_commodity")
    public String add_or_delete_hot_commodity(@RequestParam("id") Integer id,
                                              @RequestParam("pn") Integer pn,
                                              @RequestParam("result") String result) {

        if (result.equals("添加")) {
            commodityService.add_hot_commodity(id);
        } else if (result.equals("删除")) {
            commodityService.delete_hot_commodity(id);
        }
        return "redirect:/service/hotCommodity/" + pn;

    }
}
