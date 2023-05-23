package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Comment;
import com.chandler.blog.entity.User;
import com.chandler.blog.entity.vo.CommentVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.mapper.CommentMapper;
import com.chandler.blog.service.UserService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chandler.blog.service.CommentService;
import java.util.List;
import java.util.Optional;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 16:54:11
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Autowired
	private UserService userService;

	@Override
	public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
		// 查询所有根评论
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
		// 根据类型评论，查询对应的评论
		queryWrapper.eq(Comment::getType, commentType);
		queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
		queryWrapper.eq(Comment::getRootId, SystemConstants.ROOR_ID);
		Page<Comment> page = new Page<>(pageNum, pageSize);
		page(page, queryWrapper);
		List<Comment> commentList = page.getRecords();
		// 转成 vo 模型
		List<CommentVo> commentVoList = toCommentVo(commentList);
		// 给所有根评论赋值子评论数组
		for (CommentVo commentVo : commentVoList) {
			List<CommentVo> children = getChildren(commentVo.getId());
			commentVo.setChildren(children);
		}
		return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
	}

	/**
	 * 发表评论
	 * @param token 根据token获取对应的用户，发表评论
	 * @return
	 */
	@Override
	public ResponseResult addComment(String token, Comment comment) {
		// 保存评论到数据库
		save(comment);
		// 发表评论
		return ResponseResult.okResult();
	}

	/**
	 * 根据 id 查询对应的子评论集合
	 * @param id
	 */
	private List<CommentVo> getChildren(Long id) {
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Comment::getRootId, id);
		queryWrapper.orderByAsc(Comment::getCreateTime);
		List<Comment> comments = list(queryWrapper);
		return toCommentVo(comments);
	}

	/**
	 * 转化为 CommentVo 的集合对象,并赋值
	 *
	 * @param origin
	 * @return
	 */
	private List<CommentVo> toCommentVo(List<Comment> origin) {
		// 根据评论人的 id 查询对应名字
		List<CommentVo> commentVoList = BeanCopyUtils.beanCopyList(origin, CommentVo.class);
		commentVoList
				.stream()
				.forEach(commentVo -> {
					commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
					// 如果评论id非根评论，则赋值 toCommentUsername
					if (commentVo.getCreateBy() != SystemConstants.ROOR_ID) {
						User user = userService.getById(commentVo.getToCommentUserId());
						Optional.ofNullable(user).ifPresent(u -> commentVo.setToCommentUsername(u.getNickName()));
					}
				});
		return commentVoList;
	}
}

