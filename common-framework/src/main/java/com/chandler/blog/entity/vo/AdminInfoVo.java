package com.chandler.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoVo {

	/* 权限信息 */
	private List<String> permissions;

	/* 管理员角色 */
	private List<String> roles;

	/* 用户 */
	private UserInfoVo user;
}
