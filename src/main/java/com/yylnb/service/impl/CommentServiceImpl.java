package com.yylnb.service.impl;

import com.yylnb.entity.Comment;
import com.yylnb.entity.User;
import com.yylnb.mapper.CommentMapper;
import com.yylnb.mapper.UserMapper;
import com.yylnb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/6 - 16:42
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    @Transactional
    public Integer insertComment(Comment comment) {
        commentMapper.insertComment(comment);
        return commentMapper.findIdByAll(comment);
    }

    @Override
    public List<Comment> findCommentByCommodity_id(Integer commodity_id, String type) {
        List<Comment> comments = commentMapper.findCommentByCommodity_id(commodity_id, type);
        for (Comment comment : comments) {
            User user = userMapper.findUserInfoById(comment.getUser_id());
            comment.setUser(user);
        }
        return comments;
    }
}
