package com.yylnb.controller;

import com.yylnb.entity.Commodity;
import com.yylnb.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    SellerService sellerService;

    @PostMapping("/add")
    public String add(@RequestParam("user_id") Integer user_id,
                      @RequestParam("introduction") String introduction,
                      @RequestParam("carousel") String carousel,
                      @RequestParam("name") String name,
                      @RequestParam("price_before_point") String price_before_point,
                      @RequestParam("price_after_point") String price_after_point) {
        BigDecimal price = new BigDecimal(price_before_point + "." + price_after_point);
        Commodity commodity = new Commodity();
        commodity.setUser_id(user_id);
        commodity.setName(name);
        commodity.setIntroduction(introduction);
        commodity.setPrice(price);
        commodity.setCarousel(carousel);
        sellerService.insertCommodity(commodity);
        return "redirect:/transition";
    }

}
