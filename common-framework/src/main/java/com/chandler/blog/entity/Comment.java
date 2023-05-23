package com.chandler.blog.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2023-03-06 16:54:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_comment")
public class Comment {
	@TableId
	private Long id;

	//评论类型（0代表文章评论，1代表友链评论）
	private String type;
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

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 设置实际显示格式
	@TableField(fill = FieldFill.INSERT)
	private Long createBy;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 设置实际显示格式
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 设置实际显示格式
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateBy;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 设置实际显示格式
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	//删除标志（0代表未删除，1代表已删除）
	private Integer delFlag;


}
