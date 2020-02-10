package com.yylnb.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author RainLin
 * @date 2020/1/27 - 16:11
 */
@Data
public class Commodity {
    private Integer id;
    private Integer user_id;
    private String name;
    private String nick_name;
    private String introduction;
    private BigDecimal price;
    //一组图片地址存入数据库的格式是"地址,地址,地址"
    private String carousel;
    //在取出数据展示时将地址转化为数组
    private String[] carousel_array;
    //state商品的状态
    //0:审核中 1:出售中 2:已下架
    private Integer state;
    //销量
    private Integer sales;
}
