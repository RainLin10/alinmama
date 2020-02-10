package com.yylnb.service;

import com.yylnb.entity.Comment;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/6 - 16:42
 */
public interface CommentService {
    Integer insertComment(Comment comment);

    List<Comment> findCommentByCommodity_id(Integer commodity_id, String type);
}
