package com.yylnb.entity;

import lombok.Data;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/2 - 16:27
 */
@Data
public class Comment {
    private Integer id;
    private Integer commodity_id;
    private Integer user_id;
    private String comment;
    private Long comment_time;
    private String type;
    private User user;
}
