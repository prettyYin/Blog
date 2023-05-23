package com.chandler.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

	private Long id;

	private String userName;

	private String nickName;

	private String sex;

	private String status;

	private String email;

	private String phonenumber;
}
