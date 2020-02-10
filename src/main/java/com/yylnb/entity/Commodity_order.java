package com.yylnb.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author RainLin
 * @date 2020/2/2 - 20:14
 */
@Data
public class Commodity_order {
    private Integer id;
    private Integer user_id;
    private Integer seller_id;
    private Integer commodity_id;
    private Long create_time;
    // 0未发货 1已发货 2已完成
    private Integer state;
    //购买数量
    private Integer quantity;
    private BigDecimal price;
    private Integer comment_id;

    private User user;
    private Commodity commodity;
}
