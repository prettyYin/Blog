package com.chandler.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminArticleVo {

	private Long categoryId;

	private String content;

	private String isComment;

	/**
	 * 状态（0已发布，1草稿）
	 */
	private String status;

	/**
	 * 是否置顶（0否，1是）
	 */
	private String isTop;

	private Long id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 文章摘要
	 */
	private String summary;

	/**
	 * 缩略图
	 */
	private String thumbnail;

	/**
	 * 访问量
	 */
	private Long viewCount;

	/**
	 *
	 */
	private Date createTime;
}
