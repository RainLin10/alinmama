package com.yylnb.controller;

import com.yylnb.entity.Commodity;
import com.yylnb.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/1 - 14:40
 */
@Controller
public class IndexController {
    @Autowired
    CommodityService commodityService;

    /**
     * 进入主页初始化数据
     * 热门商品 热门搜索关键词
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String init(Model model,
                       HttpSession session) {
        String key = "hot_commodity";
        List<Commodity> commodities = commodityService.findCommoditiesByRedis(1, key);
        List<String> searches = commodityService.hot_search();
        session.setAttribute("searches", searches);
        model.addAttribute("commodities", commodities);
        return "index";
    }

}
