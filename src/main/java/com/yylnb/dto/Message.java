package com.yylnb.dto;

import lombok.Data;

/**
 * @author RainLin
 * @date 2020/2/24 - 21:56
 */
@Data
public class Message {
    private String send_id;
    private String msg;
    private String receiver_id;
}
