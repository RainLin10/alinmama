package com.yylnb.entity;

import lombok.Data;

/**
 * @author RainLin
 * @date 2020/1/19 - 15:52
 */
@Data
public class User {
    private Integer id;
    private String account;
    private String password;
    private String role;
}
