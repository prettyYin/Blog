package com.chandler.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginUserInfoVo implements Serializable {

	private String token;

	private UserInfoVo userInfo;
}
