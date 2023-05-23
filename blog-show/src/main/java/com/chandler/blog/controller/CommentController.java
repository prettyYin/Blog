package com.chandler.blog.controller;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.AddCommentDto;
import com.chandler.blog.entity.Comment;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.CommentService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户评论",description = "用户评论相关接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * 根据文章id分页显示对应的评论
	 *
	 * @param articleId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "根据文章id获取所有评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "articleId",value = "文章id"),
			@ApiImplicitParam(name = "pageNum",value = "页号"),
			@ApiImplicitParam(name = "pageSize",value = "每页显示的评论的条数")
	})
	@GetMapping("/commentList")
	public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
		return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
	}

	@ApiOperation(value = "获取友联所有评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum",value = "页号"),
			@ApiImplicitParam(name = "pageSize",value = "每页显示的评论的条数")
	})
	@GetMapping("/linkCommentList")
	public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
		return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum, pageSize);
	}

	/**
	 * 发表评论
	 *
	 * @param token 根据token获取对应的用户，发表评论
	 * @return
	 */
	@ApiOperation(value = "添加评论")
	@ApiImplicitParam(name = "token",value = "请求头携带的用户token信息")
	@PostMapping
	public ResponseResult addComment(@RequestHeader(value = "token", required = true) String token,
								  @RequestBody AddCommentDto addCommentDto) {
		// token 是否有效
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		// TODO 以后可以这边做过滤信息，目前值判断评论是否为空
		if (!StringUtils.hasText(addCommentDto.getContent())) {
			throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
		}
		Comment comment = BeanCopyUtils.beanCopy(addCommentDto, Comment.class);
		return commentService.addComment(token, comment);
	}
}
