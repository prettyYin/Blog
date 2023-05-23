package com.chandler.blog.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo extends ArticleVo {


	/**
	 * 所属分类id
	 */
	private Long categoryId;

	/**
	 * 所属分类名称
	 */
	@TableField(exist = false) // 在实际表情况中不存在该字段
	private String categoryName;

	/**
	 * 文章id
	 */
	@TableId
	private Long id;

	/**
	 * 文章内容
	 */
	private String content;

	/**
	 *
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss") // 设置实际显示格式
	private Date createTime;

	/**
	 * 是否允许评论 1是，0否
	 */
	private String isComment;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 访问量
	 */
	private Long viewCount;

}


