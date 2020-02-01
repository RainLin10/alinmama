package com.yylnb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yylnb.entity.Commodity;
import com.yylnb.entity.User;
import com.yylnb.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/1/31 - 16:37
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodityService;


    /**
     * 接收id查询商品 前往详情页面
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("{id}")
    public String findCommodityById(@PathVariable("id") Integer id,
                                    Model model,
                                    HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        String role = user.getRole();
        Commodity commodity = commodityService.findCommodityById(id, role);
        model.addAttribute(commodity);
        return "commodity";
    }

    @RequestMapping("/search")
    public String findCommodityBySearch(@RequestParam("pn") Integer pn,
                                        @RequestParam("search") String search,

                                        Model model) {
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);
        List<Commodity> commodities = commodityService.findCommodityBySearch(search);
        System.out.println(commodities);
        PageInfo page = new PageInfo(commodities, 10);
        model.addAttribute("title", search);
        model.addAttribute("commodities", page);
        model.addAttribute("pn", pn);
        model.addAttribute("search", search);
        return "search_commodity";
    }
}
