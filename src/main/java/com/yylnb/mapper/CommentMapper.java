package com.yylnb.mapper;

import com.yylnb.entity.Comment;
import com.yylnb.entity.Commodity_order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author RainLin
 * @date 2020/2/6 - 16:27
 */
@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment(commodity_id,user_id,comment,comment_time,type) VALUES(#{commodity_id},#{user_id},#{comment},#{comment_time},#{type})")
    void insertComment(Comment comment);

    @Select("SELECT id FROM comment WHERE commodity_id=#{commodity_id} AND user_id=#{user_id} AND comment=#{comment} AND comment_time=#{comment_time} AND type=#{type}")
    Integer findIdByAll(Comment comment);

    @Select("SELECT * FROM comment WHERE commodity_id=#{commodity_id}  AND type=#{type}")
    List<Comment> findCommentByCommodity_id(Integer commodity_id, String type);

    @Select("SELECT * FROM comment WHERE user_id=#{user_id}")
    List<Comment> findCommentByUser_id(Integer user_id);
}
