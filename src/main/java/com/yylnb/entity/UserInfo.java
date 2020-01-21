package com.yylnb.entity;

import lombok.Data;

/**
 * @author RainLin
 * @date 2020/1/19 - 17:35
 */
@Data
public class UserInfo {
    private Integer user_id;
    private String account;
    private String nick_name;
    private String introduction;
    private String isbusiness;
    private String gender;
    private String avatar;
    private String register_time;
    private String login_time;
    private String login_times;

}
