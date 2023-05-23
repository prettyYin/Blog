package com.chandler.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户登录实体类")
public class LoginUserDto {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
//	@JsonProperty("userName") // 序列化和反序列化都有用
	@JsonAlias({"username","userName"}) // 只对反序列有用
	private String userName;

	/**
	 * 密码
	 */
	@ApiModelProperty("用户密码")
	private String password;
}
