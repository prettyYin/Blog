package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Comment;
import org.omg.CORBA.SystemException;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 16:54:11
 */
public interface CommentService extends IService<Comment> {

	ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

	ResponseResult addComment(String token, Comment comment) throws SystemException;
}
