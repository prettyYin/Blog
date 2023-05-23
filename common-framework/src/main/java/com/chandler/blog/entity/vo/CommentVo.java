package com.chandler.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

	// 用户id
	private Long id;

	//文章id
	private Long articleId;

	//根评论id
	private Long rootId;

	//评论内容
	private String content;

	//所回复的目标评论的userid
	private Long toCommentUserId;

	//回复目标评论id
	private Long toCommentId;

	//被回复人名称
	private String toCommentUsername;

	private Long createBy;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss") // 设置实际显示格式
	private Date createTime;

	private String username;

	private List<CommentVo> children;
}
