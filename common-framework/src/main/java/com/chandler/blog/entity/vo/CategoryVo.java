package com.chandler.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
	private Long id;

	/**
	 * 分类名
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;
}
