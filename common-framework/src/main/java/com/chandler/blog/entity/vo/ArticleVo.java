package com.chandler.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {

	private Long id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 分类名
	 */
	private String categoryName;

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