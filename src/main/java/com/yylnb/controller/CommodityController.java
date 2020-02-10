package com.yylnb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yylnb.entity.Comment;
import com.yylnb.entity.Commodity;
import com.yylnb.entity.Commodity_order;
import com.yylnb.entity.User;
import com.yylnb.service.CommentService;
import com.yylnb.service.CommodityOrderService;
import com.yylnb.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RainLin
 * @date 2020/1/31 - 16:37
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodityService;
    @Autowired
    CommodityOrderService commodityOrderService;
    @Autowired
    CommentService commentService;

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

    @GetMapping("/getComment")
    @ResponseBody
    public Map<String, Object> getComment(@RequestParam("id") Integer id,
                                          @RequestParam("pn") Integer pn,
                                          @RequestParam("type") String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);
        List<Comment> c = commentService.findCommentByCommodity_id(id, type);
        PageInfo comments = new PageInfo(c, 10);
        map.put("comment", comments);
        return map;
    }

    @RequestMapping("/search")
    public String findCommodityBySearch(@RequestParam("pn") Integer pn,
                                        @RequestParam("search") String search,
                                        Model model) {
        if (search.equals("") || search.length() > 10) {
            return "redirect:/";
        }
        //使用分页插件，pn是页面传来的页码，6为每页最大显示数
        PageHelper.startPage(pn, 20);
        List<Commodity> commodities = commodityService.findCommodityBySearch(search);
        PageInfo page = new PageInfo(commodities, 10);
        model.addAttribute("title", search);
        model.addAttribute("commodities", page);
        model.addAttribute("pn", pn);
        model.addAttribute("search", search);
        return "search_commodity";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam("user_id") Integer user_id,
                      @RequestParam("commodity_id") Integer commodity_id,
                      @RequestParam("seller_id") Integer seller_id,
                      @RequestParam("quantity") Integer quantity,
                      @RequestParam("price") BigDecimal price) {

        Commodity_order commodity_order = new Commodity_order();
        commodity_order.setUser_id(user_id);
        commodity_order.setCommodity_id(commodity_id);
        commodity_order.setSeller_id(seller_id);
        commodity_order.setQuantity(quantity);
        commodity_order.setPrice(price);
        commodity_order.setCreate_time(System.currentTimeMillis());
        commodityOrderService.insertCommodity_order(commodity_order);
        return "redirect:/commodity/" + commodity_id;
    }

    /**
     * 确认收货
     *
     * @param id
     * @param pn
     * @param state
     * @return
     */
    @GetMapping("/received")
    public String received(
            @RequestParam("id") Integer id,
            @RequestParam("commodity_id") Integer commodity_id,
            @RequestParam("pn") Integer pn,
            @RequestParam("state") Integer state) {
        commodityService.addSales(commodity_id);
        commodityOrderService.changeCommodity_orderState(id, 2);
        return "redirect:/user/i_buy/" + state + "/" + pn;
    }

    /**
     * 确认发货
     *
     * @param id
     * @param pn
     * @return
     */
    @GetMapping("/ship")
    public String ship(
            @RequestParam("id") Integer id,
            @RequestParam("pn") Integer pn,
            @RequestParam("state") Integer state) {
        commodityOrderService.changeCommodity_orderState(id, 1);
        return "redirect:/seller/allUsersOnCommodity/" + state + "/" + pn;
    }


    /**
     * 确认收货
     *
     * @param id
     * @param pn
     * @param state
     * @return
     */
    @GetMapping("/cancel_order")
    public String cancel_order(@RequestParam("id") Integer id,
                               @RequestParam("pn") Integer pn,
                               @RequestParam("state") Integer state) {
        commodityOrderService.deleteCommodity_order(id);
        return "redirect:/seller/allUsersOnCommodity/" + state + "/" + pn;
    }

    @PostMapping("/comment")
    public String comment(@RequestParam("commodity_id") Integer commodity_id,
                          @RequestParam("user_id") Integer user_id,
                          @RequestParam("order_id") Integer order_id,
                          @RequestParam("comment") String comment,
                          @RequestParam("type") String type,
                          @RequestParam("pn") Integer pn) {
        Comment comment1 = new Comment();
        comment1.setCommodity_id(commodity_id);
        comment1.setUser_id(user_id);
        comment1.setComment(comment);
        comment1.setComment_time(System.currentTimeMillis());
        comment1.setType(type);
        Integer comment_id = commentService.insertComment(comment1);
        commodityOrderService.changeCommentIdById(order_id, comment_id);
        return "redirect:/user/i_buy/2/" + pn;
    }
}
