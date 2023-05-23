package com.chandler.blog.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateLinkDto {
	private Long id;

	private String name;

	private String logo;

	private String description;

	//网站地址
	private String address;

	private String status;
}
