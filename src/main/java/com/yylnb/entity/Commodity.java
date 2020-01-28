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
    private String introduction;
    private BigDecimal price;
    private String carousel;
}
