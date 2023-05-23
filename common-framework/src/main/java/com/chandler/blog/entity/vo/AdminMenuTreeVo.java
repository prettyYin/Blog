package com.chandler.blog.entity.vo;

import com.chandler.blog.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminMenuTreeVo{

	/* 子菜单 */
	List<AdminMenuTreeVo> children;

	/* 菜单id */
	private Long id;

	/* 菜单标签 */
	private String label;

	/* 父菜单id */
	private Long parentId;
}
