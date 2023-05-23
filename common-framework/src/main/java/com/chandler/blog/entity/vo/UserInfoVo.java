package com.chandler.blog.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo implements Serializable {

	/**
	 * 主键
	 */
	@TableId
	private Long id;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户性别（0男，1女，2未知）
	 */
	private String sex;

	/**
	 * 头像
	 */
	private String avatar;
}
