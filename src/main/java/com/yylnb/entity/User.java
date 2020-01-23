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

    private Integer user_id;
    private String nick_name;
    private String introduction;
    private String isBusiness;
    private String gender;
    private String avatar;
    private Long register_time;
    private Long login_time;
    private Integer login_times;
    private String login_ip;
}
