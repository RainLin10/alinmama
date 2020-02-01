package com.yylnb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yylnb.entity.Commodity;
import com.yylnb.entity.User;
import com.yylnb.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/27 - 15:42
 */
@Slf4j
@Controller
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    CommodityService commodityService;

    /**
     * 上架货物
     *
     * @param user_id
     * @param introduction
     * @param carousel
     * @param name
     * @param price_before_point
     * @param price_after_point
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestParam("user_id") Integer user_id,
                      @RequestParam("nick_name") String nick_name,
                      @RequestParam("introduction") String introduction,
                      @RequestParam("carousel") String carousel,
                      @RequestParam("name") String name,
                      @RequestParam("price_before_point") String price_before_point,
                      @RequestParam("price_after_point") String price_after_point) {
        BigDecimal price = new BigDecimal(price_before_point + "." + price_after_point);
        Commodity commodity = new Commodity();
        commodity.setUser_id(user_id);
        commodity.setName(name);
        commodity.setNick_name(nick_name);
        commodity.setIntroduction(introduction);
        commodity.setPrice(price);
        commodity.setCarousel(carousel);
        commodityService.insertCommodity(commodity);
        return "redirect:/transition";
    }

    /**
     * 我的在售 数据初始化
     *
     * @return
     */
    @RequestMapping("/i_sell/{state}/{pn}")
    public String i_sell(@PathVariable("pn") Integer pn,
                         @PathVariable("state") Integer state,
                         Model model,
                         HttpSession session) {
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);

        //得到用户的id
        User user = (User) session.getAttribute("userInfo");
        Integer user_id = user.getUser_id();
        //得到商品
        List<Commodity> commodities = commodityService.findCommoditiesByUserIdAndState(user_id, state);
        //包装所有商品，获得更多方法，5为显示5个页码
        PageInfo page = new PageInfo(commodities, 10);
        model.addAttribute("commodities", page);
        model.addAttribute("state", state);
        model.addAttribute("whoYou", "seller");
        model.addAttribute("title", "我的在售");
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
     * @param pn    在转发时注入
     * @param state 在转发时注入
     * @return
     */
    @GetMapping("/deleteCommodity")
    public String deleteCommodity(@RequestParam("id") Integer id,
                                  @RequestParam("pn") Integer pn,
                                  @RequestParam("state") Integer state) {

        commodityService.deleteCommodity(id);
        return "redirect:/seller/i_sell/" + state + "/" + pn;
    }

}
