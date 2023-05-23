package com.chandler.blog.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "添加评论实体类")
public class AddCommentDto {
	@ApiModelProperty("评论id")
	private Long id;

	@ApiModelProperty("（0代表文章评论，1代表友链评论）")
	//评论类型（0代表文章评论，1代表友链评论）
	private String type;

	@ApiModelProperty("文章id")
	//文章id
	private Long articleId;

	@ApiModelProperty("根评论id")
	//根评论id
	private Long rootId;

	@ApiModelProperty("评论内容")
	//评论内容
	private String content;

	@ApiModelProperty("所回复的目标评论的userid")
	//所回复的目标评论的userid
	private Long toCommentUserId;

	@ApiModelProperty("回复目标评论id")
	//回复目标评论id
	private Long toCommentId;

	@ApiModelProperty("评论创建人id")
	private Long createBy;

	@ApiModelProperty("评论创建时间")
	private Date createTime;

	@ApiModelProperty("评论更新人id")
	private Long updateBy;

	@ApiModelProperty("评论更新时间")
	private Date updateTime;

	//删除标志（0代表未删除，1代表已删除）
	@ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
	private Integer delFlag;
}
